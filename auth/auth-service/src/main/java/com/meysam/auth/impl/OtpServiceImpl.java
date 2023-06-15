package com.meysam.auth.impl;

import com.meysam.auth.api.OtpService;
import com.meysam.auth.model.enums.OtpTarget;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService {



    @Override
    public void sendOtp(String username, OtpTarget otpTarget, String destination) {
        switch (otpTarget){
            case EMAIL -> {

            }
            case PHONE -> {

            }
        }

    }

    @Override
    public boolean isValidOtpCode(String username, OtpTarget otpTarget, long otpCode) {
        return false;
    }
}
