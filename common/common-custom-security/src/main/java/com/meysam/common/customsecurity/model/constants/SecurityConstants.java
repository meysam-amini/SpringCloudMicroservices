package com.meysam.common.customsecurity.model.constants;

public interface SecurityConstants {
    String ENABLE_SECURITY = "spring.security.enable";
    String IGNORED_PATH_PROPERTY = "${spring.security.ignored.paths:#{null}}";
}
