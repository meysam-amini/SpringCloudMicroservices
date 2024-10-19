package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.entity.Admin;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.service.AdminPermissionServiceImpl;
import com.meysam.common.customsecurity.service.api.AdminRoleService;
import com.meysam.common.customsecurity.service.api.AdminService;
import com.meysam.common.customsecurity.service.RolePermissionService;
import com.meysam.common.customsecurity.service.api.PrincipleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipleServiceImpl implements PrincipleService {

    private final RedisTemplate redisTemplate;
    private final AdminService adminService;
    private final AdminPermissionServiceImpl profilePermissionService;
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
                List<Long> rolesIds = roles.stream().map(Role::getId).toList();
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


    private Admin getProfileByUsername(String usernam) {
        return adminService.getAdminByUsername(usernam);
    }

    private List<String> getProfilePermissions(Long profileId) {
        return profilePermissionService.getPermissions(profileId);
    }

    private List<String> getRolesPermissionsNames(List<Long> rolesIds) {
        return rolePermissionService.getPermissions(rolesIds);
    }

    private List<Role> getRolesNames(Long profileId) {
        return profileRoleService.getRoles(profileId);
    }
}