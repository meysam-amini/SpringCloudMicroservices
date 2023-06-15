package com.meysam.auth.api;

import com.meysam.auth.model.enums.OtpTarget;

public interface OtpService {

    boolean sendOtp(String username,OtpTarget otpTarget,String destination);
    void validateOtpCode(String username, OtpTarget otpTarget, Long enteredOtpCode);
}
