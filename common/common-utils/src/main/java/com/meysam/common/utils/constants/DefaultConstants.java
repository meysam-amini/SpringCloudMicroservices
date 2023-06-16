package com.meysam.common.utils.constants;

import java.util.HashMap;

public class DefaultConstants {

    public static HashMap<String,String> defaultConstants = new HashMap<>(){{
        put(Constants.GENERAL_PROPERTY_IS_EMAIL_OTP_ACTIVE_KEY,"true");
        put(Constants.GENERAL_PROPERTY_IS_SMS_OTP_ACTIVE_KEY,"true");
        put(Constants.GENERAL_PROPERTY_OTP_DURATION_IN_SECONDS_KEY,"60");
        put(Constants.GENERAL_PROPERTY_BAN_OTP_DURATION_IN_SECONDS_KEY,"180");
        put(Constants.GENERAL_PROPERTY_WRONG_ENTERED_OTP_COUNT_KEY,"4");
    }};
}
