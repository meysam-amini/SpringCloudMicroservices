package com.meysam.backoffice.service.auth.Impl;


import com.meysam.backoffice.service.auth.api.CachedDataService;
import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.dto.ProfileDTO;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.service.api.PermissionService;
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
public class CachedDataServiceImpl implements CachedDataService {

    public static final String BASE_PERMISSIONS_KEY = "BASE_PERMISSIONS";
    private final PermissionService permissionService;
    private final RedisTemplate redisTemplate;



    @Override
    public void refreshCache() {
        refreshBasePermissions();

    }

    private void refreshBasePermissions(){
        ValueOperations<String, List<Permission>> valueOperations = redisTemplate.opsForValue();
        Boolean hasKey;
        try {
            hasKey = valueOperations.getOperations().hasKey(BASE_PERMISSIONS_KEY);
        } catch (Exception e) {
            log.error("Exception on connecting to Redis server for getting client principle at login process at time:{} , exception is:{}", System.currentTimeMillis(), e);
            throw new BusinessException("Server error at login process");
        }
        if (Boolean.FALSE.equals(hasKey)) {
            List<Permission> basePermissions = permissionService.getBasePermissions();
            valueOperations.set(BASE_PERMISSIONS_KEY, basePermissions, 15, TimeUnit.MINUTES);

        }
    }
}
