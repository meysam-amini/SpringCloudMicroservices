package com.meysam.auth.api;

public interface NotificationService {

    void sendEmailOtp(String username,String destination,String generatedRandomCode);
    void sendSMSOtp(String username,String destination,String generatedRandomCode);
}
