package com.meysam.common.utils.log;

import com.meysam.common.model.other.LogContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class LogUtils {

    public static Optional<String> getOptionalLogIdentifier() {
        Optional<String> optionalRedValue = Optional.ofNullable(LogContextHolder.read());
        return optionalRedValue;
    }

    public static String getLogIdentifier() {
        return LogContextHolder.read();
    }
}
