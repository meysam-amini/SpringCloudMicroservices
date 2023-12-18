package com.meysam.common.customsecurity.service.api;


public interface SecurityService {
    boolean checkToken(String token);
    boolean hasActiveSession(String username);

}
