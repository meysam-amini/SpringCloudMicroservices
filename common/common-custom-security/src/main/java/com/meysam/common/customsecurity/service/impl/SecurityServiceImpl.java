package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.customsecurity.model.CheckTokenResult;
import com.meysam.common.customsecurity.service.api.SecurityService;
import com.meysam.common.customsecurity.utils.JwtUtil;
import com.meysam.common.model.constants.GlobalConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final RestTemplate simpleRestTemplate;
    private final JwtUtil jwtUtils;

    @Value("${api.checkToken.url}")
    private String CHECK_TOKEN_URL;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String CLIENT_ID;
    @Value("${oauth.client.secret}")
    private String CLIENT_SECRET;
    @Value(GlobalConstants.IS_TEST_PROPERTY)
    public boolean isTest;



    @Override
    public boolean checkToken(String token) {
       try {
           if (!isTest) {
               //if U R gonna use keycloak for check token(with keycloak access_token):
               //return callAuthServerCheckToken(token);

               //if U want just verify Ur self-generated jwt token:
               return jwtUtils.validateToken(token);
           } else {
               return true;
           }
       }catch (Exception e){
           log.info("exception in checkToken at time:{} , the exception is:{} ",System.currentTimeMillis(),e);
           return false;
       }
    }

    private boolean callAuthServerCheckToken(String token){
       //clientId & secret could be stored on db
        HttpHeaders headers = createHeaders(CLIENT_ID, CLIENT_SECRET);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        CheckTokenResult checkTokenResult = simpleRestTemplate.postForObject(CHECK_TOKEN_URL, entity, CheckTokenResult.class);
        return checkTokenResult.isActive();
    }

    private HttpHeaders createHeaders(String clientId, String clientSecret) {
        return new HttpHeaders() {{
            String auth = clientId + ":" + clientSecret;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}
