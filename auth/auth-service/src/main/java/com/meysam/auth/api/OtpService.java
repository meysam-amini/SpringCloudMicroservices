package com.meysam.auth.api;

import com.meysam.auth.model.enums.OtpTarget;
import org.springframework.http.ResponseEntity;

public interface OtpService {

    ResponseEntity sendOtp(String username, OtpTarget otpTarget,String destination,boolean isForRegisteredUser);
    void validateOtpCode(String username, OtpTarget otpTarget, Long enteredOtpCode);
    void createOtpCodeAndInitializeWrongOtpCounter(String username,OtpTarget otpTarget, String generatedRandomCode);
    void removeCachedOtpCodeAndWrongOtpCount(String username, OtpTarget otpTarget);
}
