package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.entity.Admin;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.service.AdminPermissionService;
import com.meysam.common.customsecurity.service.AdminRoleService;
import com.meysam.common.customsecurity.service.AdminService;
import com.meysam.common.customsecurity.service.RolePermissionService;
import com.meysam.common.customsecurity.service.api.PrincipleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipleServiceImpl implements PrincipleService {

    private final RedisTemplate redisTemplate;
    private final AdminService profileService;
    private final AdminPermissionService profilePermissionService;
    private final AdminRoleService profileRoleService;
    private final RolePermissionService rolePermissionService;


    @Override
    public SecurityPrinciple getSecurityPrinciple(String username) {

        ValueOperations<String, SecurityPrinciple> valueOperations = redisTemplate.opsForValue();
        String key = username;
        Boolean hasKey;
        try {
            hasKey = valueOperations.getOperations().hasKey(key);
        } catch (Exception e) {
            log.error("Exception on connecting to Redis server for getting client principle at login process at time:{} , exception is:{}", System.currentTimeMillis(), e);
            throw new BusinessException("Server error at login process");
        }
        try {
            if (Boolean.FALSE.equals(hasKey)) {
                Admin admin = null;
                admin = getProfileByUsername(username);
                List<String> directPermissions = getProfilePermissions(admin.getId());

                List<Role> roles = getRolesNames(admin.getId());
                List<BigInteger> rolesIds = roles.stream().map(Role::getId).toList();
                List<String> permissionsByRoles = getRolesPermissionsNames(rolesIds);

                permissionsByRoles.addAll(directPermissions);

                SecurityPrinciple securityPrinciple = SecurityPrinciple.builder()
                        .username(username)
                        .adminId(admin.getId())
                        .permissions(permissionsByRoles)
                        .build();
                List<String> roleNames = roles.stream().map(Role::getName).toList();
                securityPrinciple.setRoles(roleNames);
                valueOperations.set(key, securityPrinciple, 15, TimeUnit.MINUTES);
                return securityPrinciple;
            } else {
                return valueOperations.get(key);
            }
        }catch (Exception e){
            log.error("Exception on getting client principle at login process at time:{} , exception is:{}", System.currentTimeMillis(), e);
            throw new BusinessException("Server error at login process");
        }
    }

    private Admin getProfileByNationalId(String nationalId) {
        return profileService.getProfileByNationalId(nationalId);
    }

    private Admin getProfileByUsername(String usernam) {
        return profileService.getProfileByUsername(usernam);
    }

    private List<String> getProfilePermissions(BigInteger profileId) {
        return profilePermissionService.getPermissions(profileId);
    }

    private List<String> getRolesPermissionsNames(List<BigInteger> rolesIds) {
        return rolePermissionService.getPermissions(rolesIds);
    }

    private List<Role> getRolesNames(BigInteger profileId) {
        return profileRoleService.getRoles(profileId);
    }
}