package com.meysam.common.customsecurity.utils;


import com.meysam.common.customsecurity.model.ClientPrinciple;
import com.meysam.common.customsecurity.model.CustomAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

@Slf4j
public class CustomSecurityUtils {

    public static Optional<String> getCurrentClient() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.nonNull(securityContext)) {
            Authentication authentication = securityContext.getAuthentication();
            if (Objects.nonNull(authentication)) {
                try {
                    ClientPrinciple clientPrinciple = (ClientPrinciple) authentication.getPrincipal();
                    return Optional.ofNullable(clientPrinciple.getClientId());
                } catch (Exception exception) {
                    log.error("can't cast client principle to ClientPrinciple dto");
                    throw exception;
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<String> getClient(CustomAuthentication customAuthentication) {
        ClientPrinciple clientPrinciple = (ClientPrinciple) customAuthentication.getPrincipal();
        return Optional.ofNullable(clientPrinciple.getClientId());
    }

    public static String getClientThrowExceptionIfAbsent(CustomAuthentication customAuthentication) {
        return getClient(customAuthentication).orElseThrow(() -> new SecurityException("not authenticated"));
    }
}
