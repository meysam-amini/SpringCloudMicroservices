package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.customsecurity.service.api.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RedisTokenService implements TokenService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public boolean checkToken() {
        return false;
    }

    @Override
    public boolean saveToken() {
        return false;
    }
}
