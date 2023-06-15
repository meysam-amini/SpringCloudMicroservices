package com.meysam.auth.api;

import com.meysam.auth.model.enums.OtpTarget;

public interface NotificationService {

    boolean sendOtp(String username, OtpTarget otpTarget,String destination);
}
