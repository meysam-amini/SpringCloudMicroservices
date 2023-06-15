package com.meysam.auth.impl;

import com.meysam.auth.api.KeycloakService;
import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.LoginResponseDto;
import com.meysam.auth.model.dto.RegisterUserRequestDto;
import com.meysam.auth.model.dto.RegisterUserResponseDto;
import com.meysam.auth.model.entity.Role;
import com.meysam.common.entity.User;
import com.meysam.common.exception.BusinessException;
import com.meysam.common.messages.LocaleMessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    @Qualifier(value = "SimpleRestTemplate")
    private final RestTemplate restTemplate;
    private final LocaleMessageSourceService messageSourceService;

    @Value("${keycloak.get.token.url}")
    private String KEYCLOAK_GET_TOKEN_URL;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String CLIENT_ID;
    @Value("${oauth.client.secret}")
    private String CLIENT_SECRET;
    @Value("${oauth.grant.type}")
    private String GRANT_TYPE;

    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerDto) {
        return null;
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginDto) {

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("username",loginDto.getUsername());
        data.add("password",loginDto.getPassword());
        data.add("client_id",CLIENT_ID);
        data.add("client_secret",CLIENT_SECRET);
        data.add("grant_type",GRANT_TYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data,headers);
        try {
            ResponseEntity<LoginResponseDto> response = restTemplate
                    .exchange(KEYCLOAK_GET_TOKEN_URL, HttpMethod.POST, request, LoginResponseDto.class);
            LoginResponseDto loginResponseDto = response.getBody();

            if(response.getStatusCode()!=HttpStatus.OK) {
                throw new BusinessException(messageSourceService.getMessage("LOGIN_USER_FAILED"));
            }else {
                return loginResponseDto;
            }

        }catch (Exception e){
            log.error("Exception on get token from keycloak at keycloakServiceImpl at time :{}, exception is:{}",System.currentTimeMillis(),e);
            throw new BusinessException(messageSourceService.getMessage("FAILED_TO_CONNECT_TO_KEYCLOAK"));
        }


    }

    @Override
    public Role getUserRole() {
        return null;
    }

    @Override
    public User assignRole(User user) {
        return null;
    }
}
