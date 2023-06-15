package com.meysam.auth.impl;

import com.meysam.auth.api.NotificationService;
import com.meysam.auth.api.OtpService;
import com.meysam.auth.model.enums.OtpTarget;
import com.meysam.common.constants.Constants;
import com.meysam.common.exception.BusinessException;
import com.meysam.common.messages.LocaleMessageSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    LocaleMessageSourceService messageSourceService;
    private final NotificationService notificationService;
    private final RedisTemplate redisTemplate;


    @Override
    public boolean sendOtp(String username, OtpTarget otpTarget, String destination) {
        boolean sendingOtpResult = false;
        switch (otpTarget){
            case EMAIL -> {
                sendingOtpResult = notificationService.sendEmailOtp(username,destination);
            }
            case PHONE -> {
                sendingOtpResult = notificationService.sendSMSOtp(username,destination);
            }
        }
        if(sendingOtpResult){
            // add otp to redis with username,target prefix
            return true;
        }
        return false;

    }

    @Override
    public void validateOtpCode(String username, OtpTarget otpTarget, Long enteredOtpCode) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();        String key = Constants.OTP_PREFIX +otpTarget+"_"+username;
        String cachedCode = valueOperations.get(key);
        if (cachedCode==null) {
            throw new BusinessException(messageSourceService.getMessage("PLEASE_GET_THE_OTP_CODE_FIRST"));
        }
        if(Long.parseLong(cachedCode)!=enteredOtpCode){
            throw new BusinessException(messageSourceService.getMessage("WRONG_OTP_CODE"));
        }
    }
}
