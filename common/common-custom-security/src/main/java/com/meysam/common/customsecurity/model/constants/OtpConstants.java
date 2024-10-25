package com.meysam.common.customsecurity.model.constants;

public class OtpConstants {

    public enum OtpType{
        EMAIL_,
        SMS_
    }

    public enum OtpOperation {
        RESET_PASS_,
        RegistrationCommitment
    }

    public enum Prefixes{
        OTP_,
        WRONG_OTP_COUNTS_

    }

}
