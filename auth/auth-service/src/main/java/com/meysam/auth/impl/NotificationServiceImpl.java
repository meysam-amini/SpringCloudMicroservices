package com.meysam.auth.impl;

import com.meysam.auth.api.NotificationService;
import com.meysam.common.constants.Constants;
import com.meysam.common.exception.BusinessException;
import com.meysam.common.messages.LocaleMessageSourceService;
import com.meysam.common.service.api.GeneralPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final GeneralPropertiesService generalPropertiesService;
    private final LocaleMessageSourceService messageSourceService;


    @Override
    public boolean sendEmailOtp(String username, String destination) {
        boolean isEmailOtpActive = Boolean.parseBoolean(generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_IS_EMAIL_OTP_ACTIVE_KEY));
        if (isEmailOtpActive){
            //send email using smtp logics
            return true;
        }else {
            throw new BusinessException(messageSourceService.getMessage("EMAIL_OTP_IS_NOT_ACTIVE"));
        }
    }

    @Override
    public boolean sendSMSOtp(String username, String destination) {
        boolean isSMSOtpActive = Boolean.parseBoolean(generalPropertiesService.findSettingByKey(Constants.GENERAL_PROPERTY_IS_SMS_OTP_ACTIVE_KEY));
        if (isSMSOtpActive){
            //send SMS logics
            return true;
        }else {
            throw new BusinessException(messageSourceService.getMessage("SMS_OTP_IS_NOT_ACTIVE"));
        }
    }
}
