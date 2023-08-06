package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.customsecurity.constants.GlobalConstants;
import com.meysam.common.customsecurity.service.api.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Value("${api.checkToken.url}")
    private String checkTokenUrl;
    @Autowired
    private RestTemplate simpleRestTemplate;
    @Value(GlobalConstants.IS_TEST_PROPERTY)
    public boolean isTest;

    @Override
    public boolean checkToken(String token) {
        if (!isTest) {
            // TODO: 03.07.23 sajad : fake implementation, should complete it
            Boolean result = simpleRestTemplate.exchange(checkTokenUrl, HttpMethod.GET, null, Boolean.class).getBody();
            return result;
        } else {
            return true;
        }
    }
}
