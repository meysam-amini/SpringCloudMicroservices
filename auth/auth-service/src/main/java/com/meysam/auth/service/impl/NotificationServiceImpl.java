package com.meysam.auth.service.impl;

import com.meysam.auth.service.api.NotificationService;
import com.meysam.common.configs.constants.Constants;
import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.service.api.GeneralPropertiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final GeneralPropertiesService generalPropertiesService;
    private final LocaleMessageSourceService messageSourceService;


    @Override
    public void sendEmailOtp(String username, String destination,String generatedRandomCode) {
        boolean isEmailOtpActive = Boolean.parseBoolean(generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_IS_EMAIL_OTP_ACTIVE_KEY));
        if (isEmailOtpActive){
            try {
                //send email using smtp logics
            }catch (Exception e){
                log.error("Couldn't send email otp at time: {} , exception : {}",System.currentTimeMillis(),e);
                throw new BusinessException(messageSourceService.getMessage("SEND_EMAIL_OTP_FAILED"));
            }
        }else {
            throw new BusinessException(messageSourceService.getMessage("EMAIL_OTP_IS_NOT_ACTIVE"));
        }
    }

    @Override
    public void sendSMSOtp(String username, String destination,String generatedRandomCode) {
        boolean isSMSOtpActive = Boolean.parseBoolean(generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_IS_SMS_OTP_ACTIVE_KEY));
        if (isSMSOtpActive){
            try {
                //send sms logics
            }catch (Exception e){
                log.error("Couldn't send sms otp at time: {} , exception : {}",System.currentTimeMillis(),e);
                throw new BusinessException(messageSourceService.getMessage("SEND_SMS_OTP_FAILED"));
            }
        }else {
            throw new BusinessException(messageSourceService.getMessage("SMS_OTP_IS_NOT_ACTIVE"));
        }
    }
}
