package com.meysam.backoffice.service.auth.Impl;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.dto.AdminLoginResponseDto;
import com.meysam.common.customsecurity.model.dto.RegisterAdminRequestDto;
import com.meysam.common.customsecurity.model.entity.Admin;
import com.meysam.common.customsecurity.model.entity.AdminRole;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.service.AdminRoleService;
import com.meysam.common.customsecurity.service.AdminService;
import com.meysam.common.customsecurity.service.RoleService;
import com.meysam.common.customsecurity.service.api.PrincipleService;
import com.meysam.common.customsecurity.utils.JwtUtil;
import com.meysam.common.model.dto.*;
import com.meysam.common.service.api.AdminAuthServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final LocaleMessageSourceService messageSourceService;
//    private final AdminAuthServiceClient authServiceClient;
    private final PrincipleService principleService;
    private final JwtUtil jwtUtils;
    private final AdminService adminService;
    private final AdminRoleService adminRoleService;
    private final RoleService roleService;


    @Override
    public ResponseEntity<AdminLoginResponseDto> login(LoginRequestDto loginRequestDto) {
        //call sso for getting username & roles from access_token's data(in this approach we have login/register
        // on keycloak side):
        //LoginResponseDto SSOLoginResponseDto = authServiceClient.adminLogin(loginRequestDto).getBody();
        //and then just create a jwt with permissions of role(we don't store roles on our side)


        //or just check the password and create token based on username(in this approach, we keep admin's info on our side
        // and there would be no call to keycloak on login/register):

        // TODO: 02.12.23 : check password correctness and then:
        AdminLoginResponseDto loginResponseDto = AdminLoginResponseDto.builder()
                .token(generateTokenForAdmin(loginRequestDto.getUsername()))
                .build();
        return ResponseEntity.ok(loginResponseDto);

    }

    @Override
    public ResponseEntity<String> logout(String username) {
        boolean loggedout;
        loggedout = principleService.removeSession(username);
        if(loggedout){
            return ResponseEntity.ok(messageSourceService.getMessage("LOG_OUT_SUCCESS"));
        }else {
            return ResponseEntity.ok(messageSourceService.getMessage("SESSION_EXPIRED_ALREADY"));
        }
    }

    @Transactional
    @Override
    public ResponseEntity register(RegisterAdminRequestDto registerAdminRequestDto) {
        //insert admin to Admin table after successful register if keycloak was used:
        //return authServiceClient.registerAdmin(registerUserRequestDto);

        //but we store admins' data on our side, not keycloak. so:
        try {
            Role role = roleService.findRoleByName(registerAdminRequestDto.getRoleName());
            Admin admin = new Admin();
            admin.setFirstName(registerAdminRequestDto.getFirstName());
            admin.setLastName(registerAdminRequestDto.getLastName());
            // TODO: 02.12.23 : encrypt password!
            admin.setPassword(registerAdminRequestDto.getPassword());
            admin.setActiveFrom(new Date());
            admin.setActiveTo(new Date());
            admin.setMobileNumber(registerAdminRequestDto.getPhone());
            admin.setUsername(registerAdminRequestDto.getUsername());
            adminService.add(admin);

            AdminRole adminRole = new AdminRole();
            adminRole.setAdmin(admin.getId());
            adminRole.setRole(role.getId());

            adminRoleService.add(adminRole);
        }catch (Exception e){
            log.error("Exception on registering new admin process at time:{}, exception is:{}",System.currentTimeMillis(),e);
            throw new BusinessException(messageSourceService.getMessage("REGISTER_ASMIN_FAILED"));
        }
        return ResponseEntity.ok().body("register successful for username: "+ registerAdminRequestDto.getUsername());
    }


    private String generateTokenForAdmin(String username) {
        SecurityPrinciple principle = principleService.getSecurityPrinciple(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("profileId", principle.getAdminId());
        claims.put("roles", commaSeperated(principle.getRoles()));
        claims.put("permissions", commaSeperated(principle.getPermissions()));
        String token = jwtUtils.doGenerateToken(claims, username);
        return token;
    }

    private String commaSeperated(List<String> strings){
        return String.join(",", strings);
    }

}
