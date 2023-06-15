package com.meysam.auth.api;

import com.meysam.auth.model.enums.OtpTarget;

public interface OtpService {

    void sendOtp(String username,OtpTarget otpTarget,String destination);
    boolean isValidOtpCode(String username, OtpTarget otpTarget,long otpCode);
}
