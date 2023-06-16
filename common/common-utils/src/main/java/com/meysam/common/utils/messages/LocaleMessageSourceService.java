package com.meysam.common.utils.messages;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;


@Component
public class LocaleMessageSourceService {

    private final ZoneId zoneId = TimeZone.getTimeZone("GMT+3:30").toZoneId();

    @Resource
    private MessageSource messageSource;

    private static LocaleMessageSourceService instance;

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }

    public static LocaleMessageSourceService getInstance() {
        return instance;
    }

    public String getMessage(String code) {
        return getMessage(code, null);
    }


    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }


    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
