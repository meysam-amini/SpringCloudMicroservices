package com.meysam.common.customsecurity.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class CustomAuthentication implements Authentication {
    private SecurityPrinciple clientPrinciple;

    public CustomAuthentication(SecurityPrinciple clientPrinciple) {
        this.clientPrinciple = clientPrinciple;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.addAll(clientPrinciple.getPermissions().stream().
                map(p -> new SimpleGrantedAuthority("PERMISSION_" + p)).toList());
        authorities.addAll(clientPrinciple.getRoles().stream().
                map(r -> new SimpleGrantedAuthority("ROLE_" + r)).toList());

        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return clientPrinciple;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
