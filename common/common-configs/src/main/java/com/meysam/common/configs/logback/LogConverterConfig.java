package com.meysam.common.configs.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogConverterConfig extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String name = event.getLoggerName();
        return name.substring(name.lastIndexOf(".") + 1);
    }
}