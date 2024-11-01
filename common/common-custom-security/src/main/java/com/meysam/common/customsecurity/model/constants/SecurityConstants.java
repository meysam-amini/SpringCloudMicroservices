package com.meysam.common.customsecurity.model.constants;

public interface SecurityConstants {
    String ENABLE_SECURITY = "${spring.security.enabled}";
    String IGNORED_PATH_PROPERTY = "${spring.security.ignored.paths:#{/public/**}}";
}
