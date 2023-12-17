package com.meysam.common.customsecurity.service.api;


import com.meysam.common.customsecurity.model.SecurityPrinciple;

public interface PrincipleService {
    SecurityPrinciple getSecurityPrinciple(String username);

    boolean removeSession(String username);
}
