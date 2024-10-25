package com.meysam.common.customsecurity.service.impl;


import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.constants.OtpConstants;
import com.meysam.common.customsecurity.model.dto.RestResponseDTO;
import com.meysam.common.customsecurity.service.api.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final LocaleMessageSourceService messageSourceService;
    private final NotificationService notificationService;
    private final RedisTemplate redisTemplate;
    private int OTP_TTL=120;
    private int WRONG_OTP_TTL=5;
    private int WRONG_OTP_COUNT=3;

    @Override
    public ResponseEntity<RestResponseDTO> sendOtp(String msg, String username, OtpConstants.OtpType otpTarget, OtpConstants.OtpOperation otpOperation, String destination) {

        checkBanOtp(username,otpTarget,otpOperation);

        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            String otpKey = OtpConstants.Prefixes.OTP_.name() + otpTarget+otpOperation+ username;
            HashMap<String, Object> response = new HashMap<>();
            if (valueOperations.getOperations().hasKey(otpKey)) {
                Long ttl = valueOperations.getOperations().getExpire(otpKey);
                response.put("ttl", ttl);
                response.put("message", messageSourceService.getMessage("OTP_ALREADY_SENT"));
                return ResponseEntity.ok(RestResponseDTO.generate(false,0,response));
            }
        /*if(foundUser==null && isForRegisteredUser){
            throw new BusinessException(messageSourceService.getMessage("USERNAME_NOT_FOUND"));
        }*/
            int generatedRandomCode = (1000 + (int) (Math.random() * 9999));
            createOtpCodeAndInitializeWrongOtpCounter(username, otpTarget,otpOperation, generatedRandomCode);
            if (Objects.isNull(msg)) {
                msg = messageSourceService.getMessage("YOUR_OTP_IS") + " " + generatedRandomCode;
            } else {
                msg = msg + " " + generatedRandomCode;
            }

            switch (otpTarget) {
                case EMAIL_ -> {
                    // for registration, foundUser would be null
                    notificationService.sendNotification(msg, NotificationConstants.NotificationType.EMAIL, destination, false);
                }
                case SMS_ -> {
                    notificationService.sendNotification(msg, NotificationConstants.NotificationType.SMS, destination, false);
                }
            }
            return ResponseEntity.ok(RestResponseDTO.generate(false,0,messageSourceService.getMessage("SEND_OTP_SUCCESS") + " to " + destination));
        }catch (BusinessException e){
            throw e;
        }
        catch (Exception e){
            log.error("Exception on sending otp at time :{}, msg:{}, username: {}, destination: {}, exception is :",System.currentTimeMillis(),msg,username,destination,e);
            throw new BusinessException(messageSourceService.getMessage("PROBLEM_ON_SENDING_OTP"));
        }
    }

    @Override
    public void validateOtpCode(String username, OtpConstants.OtpType otpTarget, OtpConstants.OtpOperation otpOperation, int enteredOtpCode) {
        try {
            checkBanOtp(username, otpTarget,otpOperation);
            ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
            String key = OtpConstants.Prefixes.OTP_.name() + otpTarget+otpOperation+username;
            Integer cachedCode = valueOperations.get(key);
            if (cachedCode == null) {
                throw new BusinessException(messageSourceService.getMessage("PLEASE_GET_THE_OTP_CODE_FIRST"));
            }
            if (cachedCode != enteredOtpCode) {
                decreaseWrongOtpCounter(username, otpTarget,otpOperation);
                throw new BusinessException(messageSourceService.getMessage("WRONG_OTP_CODE"));
            }
        }catch (Exception e){
            throw new BusinessException(messageSourceService.getMessage("PROBLEM_ON_VALIDATING_OTP"));
        }
    }

    private void createOtpCodeAndInitializeWrongOtpCounter(String username,OtpConstants.OtpType otpTarget,OtpConstants.OtpOperation otpOperation,  int generatedRandomCode) {
        String otpKey = OtpConstants.Prefixes.OTP_.name()+otpTarget+otpOperation+username;
        String wrongOtpCounterKey = OtpConstants.Prefixes.WRONG_OTP_COUNTS_.name()+otpTarget+otpOperation+username;

        try {
            ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
//            int otpTtl = Integer.parseInt(generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_OTP_DURATION_IN_SECONDS_KEY));
            int otpTtl = OTP_TTL;
            valueOperations.set(otpKey,generatedRandomCode,otpTtl, TimeUnit.SECONDS);

//            int wrongOtpCounterTtl = Integer.parseInt(generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_BAN_OTP_DURATION_IN_SECONDS_KEY));
            int wrongOtpCounterTtl = WRONG_OTP_TTL;
//            String wrongOtpCounter = generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_WRONG_ENTERED_OTP_COUNT_KEY);
            int wrongOtpCounter = WRONG_OTP_COUNT;
            valueOperations.set(wrongOtpCounterKey,wrongOtpCounter,wrongOtpCounterTtl, TimeUnit.SECONDS);

        }catch (Exception e){
            log.error("Couldn't set OTP on Redis at time :{}, for username:{} , exception: {}",System.currentTimeMillis(),username,e);
            throw new BusinessException(messageSourceService.getMessage("SEND_OTP_FAILED"));
        }

    }

    @Override
    public void removeCachedOtpCodeAndWrongOtpCount(String username, OtpConstants.OtpType otpTarget, OtpConstants.OtpOperation otpOperation) {
        String otpKey = OtpConstants.Prefixes.OTP_.name() +otpTarget+otpOperation+username;
        String wrongOtpCounterKey = OtpConstants.Prefixes.WRONG_OTP_COUNTS_.name() +otpTarget+otpOperation+username;

        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.getOperations().delete(otpKey);
            valueOperations.getOperations().delete(wrongOtpCounterKey);

        }catch (Exception e){
            log.error("Couldn't delete cached OTP code from Redis at time :{} , exception: {}",System.currentTimeMillis(),e);
        }

    }

    private Integer decreaseWrongOtpCounter(String username, OtpConstants.OtpType otpTarget,OtpConstants.OtpOperation otpOperation) {
        String wrongOtpCounterKey = OtpConstants.Prefixes.WRONG_OTP_COUNTS_.name()+otpTarget+otpOperation+username;

        ValueOperations<String,Integer> valueOperations = redisTemplate.opsForValue();
        if (valueOperations.getOperations().hasKey(wrongOtpCounterKey)) {
            Integer count = valueOperations.get(wrongOtpCounterKey);
            count--;
            valueOperations.set(wrongOtpCounterKey, count); /*valueOperations.getOperations().getExpire(wrongOtpCounterKey), TimeUnit.SECONDS);*/
            return count;
        }
        return -1;
    }

    private void checkBanOtp(String username, OtpConstants.OtpType otpTarget,OtpConstants.OtpOperation otpOperation) {
        String wrongOtpCounterKey = OtpConstants.Prefixes.WRONG_OTP_COUNTS_.name() +otpTarget+otpOperation+username;
        ValueOperations<String,Integer> valueOperations = redisTemplate.opsForValue();
        if(valueOperations.getOperations().hasKey(wrongOtpCounterKey)){
            Integer count = valueOperations.get(wrongOtpCounterKey);
            if(count<0){
                throw new BusinessException("You are banned for "+ valueOperations.getOperations().getExpire(wrongOtpCounterKey)+" seconds!");
            }
        }
    }

}
