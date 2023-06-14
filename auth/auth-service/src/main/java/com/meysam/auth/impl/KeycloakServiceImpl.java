package com.meysam.auth.impl;

import com.meysam.auth.model.dto.KeycloakLoginRequestDto;
import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.LoginResponseDto;
import com.meysam.auth.model.dto.RegisterUserRequestDto;
import com.meysam.auth.model.entity.Role;
import com.meysam.common.entity.User;
import com.meysam.auth.api.KeycloakService;
import com.meysam.common.exception.BusinessException;
import com.meysam.common.messages.LocaleMessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public User registerUser(RegisterUserRequestDto registerDto) {
        return null;
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginDto) {

        KeycloakLoginRequestDto keycloakLoginRequestDto = KeycloakLoginRequestDto.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(loginDto.getUsername())
                .password(loginDto.getPassword())
                .build();

        HttpEntity<KeycloakLoginRequestDto> request = new HttpEntity<>(keycloakLoginRequestDto);
        ResponseEntity<LoginResponseDto> response = restTemplate
                .exchange(KEYCLOAK_GET_TOKEN_URL, HttpMethod.POST, request, LoginResponseDto.class);
        LoginResponseDto loginResponseDto = response.getBody();

        if(response.getStatusCode()!=HttpStatus.OK) {
            throw new BusinessException(messageSourceService.getMessage("LOGIN_USER_FAILED"));
        }else {
            return loginResponseDto;
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
