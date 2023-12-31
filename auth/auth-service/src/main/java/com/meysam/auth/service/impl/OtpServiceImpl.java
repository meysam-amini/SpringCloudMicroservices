package com.meysam.auth.service.impl;

import com.meysam.auth.model.enums.OtpTarget;
import com.meysam.auth.service.api.NotificationService;
import com.meysam.auth.service.api.OtpService;
import com.meysam.common.configs.constants.Constants;
import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.model.entity.Member;
import com.meysam.common.service.api.GeneralPropertiesService;
import com.meysam.common.service.api.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final LocaleMessageSourceService messageSourceService;
    private final NotificationService notificationService;
    private final RedisTemplate redisTemplate;
    private final MemberService userService;
    private final GeneralPropertiesService generalPropertiesService;


    @Override
    public ResponseEntity sendOtp(String username, OtpTarget otpTarget,String destination,boolean isForRegisteredUser) {

        checkBanOtp(username,otpTarget);

        ValueOperations valueOperations = redisTemplate.opsForValue();
        String otpKey = Constants.OTP_PREFIX +otpTarget+"_"+username;
        HashMap<String,Object> response = new HashMap<>();
        if(valueOperations.getOperations().hasKey(otpKey)){
            Long ttl = valueOperations.getOperations().getExpire(otpKey);
            response.put("ttl",ttl);
            response.put("message",messageSourceService.getMessage("OTP_ALREADY_SENT"));
            return ResponseEntity.ok(response);
        }
        Member foundUser = userService.findByUserName(username);

        if(foundUser==null && isForRegisteredUser){
            throw new BusinessException(messageSourceService.getMessage("USERNAME_NOT_FOUND"));
        }
        String generatedRandomCode = (1000+(int) (Math.random()*9999))+"";
        createOtpCodeAndInitializeWrongOtpCounter(username,otpTarget,generatedRandomCode);

        switch (otpTarget){
            case EMAIL -> {
                // for registration, foundUser would be null
                destination = foundUser==null? destination:foundUser.getEmail();
                notificationService.sendEmailOtp(username,destination,generatedRandomCode);
            }
            case PHONE -> {
                destination = foundUser==null? destination:foundUser.getPhone();
                notificationService.sendSMSOtp(username,destination,generatedRandomCode);
            }
        }
        return ResponseEntity.ok(messageSourceService.getMessage("SEND_OTP_SUCCESS")+" to "+destination);
    }

    @Override
    public void validateOtpCode(String username, OtpTarget otpTarget, Long enteredOtpCode) {
        checkBanOtp(username,otpTarget);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = Constants.OTP_PREFIX +otpTarget+"_"+username;
        String cachedCode = valueOperations.get(key);
        if (cachedCode==null) {
            throw new BusinessException(messageSourceService.getMessage("PLEASE_GET_THE_OTP_CODE_FIRST"));
        }
        if(Long.parseLong(cachedCode)!=enteredOtpCode){
            decreaseWrongOtpCounter(username,otpTarget);
            throw new BusinessException(messageSourceService.getMessage("WRONG_OTP_CODE"));
        }
    }

    @Override
    public void createOtpCodeAndInitializeWrongOtpCounter(String username,OtpTarget otpTarget, String generatedRandomCode) {
        String otpKey = Constants.OTP_PREFIX +otpTarget+"_"+username;
        String wrongOtpCounterKey = Constants.WRONG_OTP_COUNTS_PREFIX +otpTarget+"_"+username;

        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            int otpTtl = Integer.parseInt(generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_OTP_DURATION_IN_SECONDS_KEY));
            valueOperations.set(otpKey,generatedRandomCode,otpTtl, TimeUnit.SECONDS);

            int wrongOtpCounterTtl = Integer.parseInt(generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_BAN_OTP_DURATION_IN_SECONDS_KEY));
            String wrongOtpCounter = generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_WRONG_ENTERED_OTP_COUNT_KEY);
            valueOperations.set(wrongOtpCounterKey,wrongOtpCounter,wrongOtpCounterTtl, TimeUnit.SECONDS);

        }catch (Exception e){
            log.error("Couldn't set OTP on Redis at time :{} , exception: {}",System.currentTimeMillis(),e);
            throw new BusinessException(messageSourceService.getMessage("SEND_OTP_FAILED"));
        }

    }

    @Override
    public void removeCachedOtpCodeAndWrongOtpCount(String username, OtpTarget otpTarget) {
        String otpKey = Constants.OTP_PREFIX +otpTarget+"_"+username;
        String wrongOtpCounterKey = Constants.WRONG_OTP_COUNTS_PREFIX +otpTarget+"_"+username;

        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.getOperations().delete(otpKey);
            valueOperations.getOperations().delete(wrongOtpCounterKey);

        }catch (Exception e){
            log.error("Couldn't delete cached OTP code from Redis at time :{} , exception: {}",System.currentTimeMillis(),e);
        }

    }

    private Integer decreaseWrongOtpCounter(String username, OtpTarget otpTarget) {
        String wrongOtpCounterKey = Constants.WRONG_OTP_COUNTS_PREFIX +otpTarget+"_"+username;

        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        if (valueOperations.getOperations().hasKey(wrongOtpCounterKey)) {
            Integer count = Integer.valueOf(valueOperations.get(wrongOtpCounterKey));
            count--;
            valueOperations.set(wrongOtpCounterKey, String.valueOf(count)); /*valueOperations.getOperations().getExpire(wrongOtpCounterKey), TimeUnit.SECONDS);*/
            return count;
        }
        return -1;
    }

    private void checkBanOtp(String username, OtpTarget otpTarget) {
        String wrongOtpCounterKey = Constants.WRONG_OTP_COUNTS_PREFIX +otpTarget+"_"+username;
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        if(valueOperations.getOperations().hasKey(wrongOtpCounterKey)){
            Integer count = Integer.valueOf(valueOperations.get(wrongOtpCounterKey));
            if(count<0){
                throw new BusinessException("You are banned for "+ valueOperations.getOperations().getExpire(wrongOtpCounterKey)+" seconds!");
            }
        }
    }

}
