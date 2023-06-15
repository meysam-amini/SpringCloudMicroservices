package com.meysam.auth.api;

import com.meysam.auth.model.enums.OtpTarget;

public interface NotificationService {

    boolean sendEmailOtp(String username,String destination);
    boolean sendSMSOtp(String username,String destination);
}
