package com.meysam.backoffice.service.cached.impl;


import com.meysam.backoffice.service.cached.api.CachedDataService;
import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.service.api.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.meysam.common.model.constants.GlobalConstants.BASE_PERMISSIONS_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class CachedDataServiceImpl implements CachedDataService {

    private final PermissionService permissionService;
    private final RedisTemplate redisTemplate;


    @Override
    public void refreshCache() {
        refreshBasePermissions();

    }

    private void refreshBasePermissions() {
        try {
            ValueOperations<String, HashMap<Long,Permission>> valueOperations = redisTemplate.opsForValue();
            HashMap<Long,Permission> basePermissionsMap = permissionService.getBasePermissions();
            valueOperations.set(BASE_PERMISSIONS_KEY, basePermissionsMap);
        } catch (Exception e) {
            log.error("Exception on connecting to Redis server for refreshing base permissions cache at time:{} , exception is:{}", System.currentTimeMillis(), e);
            throw new BusinessException("Server error refreshing permissions cache!");
        }
    }
}
