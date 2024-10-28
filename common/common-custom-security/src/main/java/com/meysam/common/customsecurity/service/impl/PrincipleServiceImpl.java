package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.dto.ProfileDTO;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.service.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipleServiceImpl implements PrincipleService {

    private final RedisTemplate redisTemplate;
    private final ProfileService adminService;
    private final ProfilePermissionService profilePermissionService;
    private final ProfileRoleService profileRoleService;
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
                ProfileDTO profile = null;
                profile = getProfileByUsername(username);

                List<Role> roles = getRolesNames(profile.getId());
//                List<Long> rolesIds = roles.stream().map(Role::getId).toList();
                List<String> permissions = getPermissions(profile.getId());

                SecurityPrinciple securityPrinciple = SecurityPrinciple.builder()
                        .username(username)
                        .adminId(profile.getId())
                        .permissions(permissions)
                        .build();
                List<String> roleNames = roles.stream().map(Role::getName).toList();
                securityPrinciple.setRoles(roleNames);
                valueOperations.set(key, securityPrinciple, 15, TimeUnit.MINUTES);
                return securityPrinciple;
            } else {
                return valueOperations.get(key);
            }
        }
        catch (BusinessException e){
            throw e;
        }
        catch (Exception e){
            log.error("Exception on getting client principle at login process at time:{} , exception is:{}", System.currentTimeMillis(), e);
            throw new BusinessException("Server error at login process");
        }
    }

    @Override
    public boolean removeSession(String username) {
        ValueOperations<String, SecurityPrinciple> valueOperations = redisTemplate.opsForValue();
        String key = username;
        Boolean hasKey;
        try {
            return Boolean.TRUE.equals(valueOperations.getOperations().delete(key));
        } catch (Exception e) {
            log.error("Exception on connecting to Redis server for removing client principle at logout process at time:{} , exception is:{}", System.currentTimeMillis(), e);
            throw new BusinessException("Server error at logout process");
        }
    }


    private ProfileDTO getProfileByUsername(String username) {
        return adminService.getProfileByUsername(username);
    }

    private List<String> getPermissions(Long profileId) {
        return profilePermissionService.getAllRolePermissions(profileId)
                .stream().map(PermissionDTO::getEnKey).collect(Collectors.toList());
    }

    private List<Role> getRolesNames(Long profileId) {
        return profileRoleService.getRoles(profileId);
    }
}