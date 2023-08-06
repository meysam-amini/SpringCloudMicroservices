package com.meysam.common.customsecurity.service.api;

public interface TokenService {
    String getToken();

    boolean checkToken();

    boolean saveToken();

}
