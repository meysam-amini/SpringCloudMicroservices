package com.meysam.auth.impl;

import com.meysam.auth.api.NotificationService;
import com.meysam.auth.model.enums.OtpTarget;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {


    @Override
    public boolean sendOtp(String username, OtpTarget otpTarget, String destination) {
        return false;
    }
}
