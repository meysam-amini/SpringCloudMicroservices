package com.meysam.common.customsecurity.utils;

import java.util.UUID;

public class GeneralUtils {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
