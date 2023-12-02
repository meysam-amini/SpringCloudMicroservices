package com.meysam.common.customsecurity.model;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

@Data
public class CustomSecurityContext implements SecurityContext {
    private Authentication authentication;

    public CustomSecurityContext(SecurityPrinciple clientPrinciple) {
        authentication = new CustomAuthentication(clientPrinciple);
        authentication.setAuthenticated(true);
    }


    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
