package com.meysam.backoffice.service.auth.Impl;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.service.api.PrincipleService;
import com.meysam.common.customsecurity.utils.JwtUtil;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.LoginResponseDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.service.api.AdminAuthServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final LocaleMessageSourceService messageSourceService;
    private final AdminAuthServiceClient authServiceClient;
    private final PrincipleService principleService;
    private final JwtUtil jwtUtils;


    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        /* 1. check user existence and its password
        *  2.
        * */
        //check for admin existance in db, if wasn't present
        generateTokenForAdmin(loginRequestDto.getUsername());
        return authServiceClient.adminLogin(loginRequestDto);
    }

    @Override
    public ResponseEntity register(RegisterUserRequestDto registerUserRequestDto) {
        //insert admin to Admin table after successful register
        return authServiceClient.registerAdmin(registerUserRequestDto);
    }

    private String generateTokenForAdmin(String username) {
        SecurityPrinciple principle = principleService.getSecurityPrinciple(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("nationalId", null);
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
