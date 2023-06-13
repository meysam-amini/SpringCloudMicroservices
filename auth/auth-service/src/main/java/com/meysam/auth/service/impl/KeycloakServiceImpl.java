package com.meysam.auth.service.impl;

import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.LoginResponseDto;
import com.meysam.auth.model.dto.RegisterUserRequestDto;
import com.meysam.auth.model.entity.Role;
import com.meysam.auth.model.entity.User;
import com.meysam.auth.service.api.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final RestTemplate restTemplate;

    @Value("${keycloak.login.url}")
    private String KEYCLOAK_LOGIN_URL;

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

        HttpEntity<LoginRequestDto> request = new HttpEntity<>(loginDto);
        ResponseEntity<LoginResponseDto> response = restTemplate
                .exchange(KEYCLOAK_LOGIN_URL, HttpMethod.POST, request, LoginResponseDto.class);
        LoginResponseDto loginResponseDto = response.getBody();
        if(response.getStatusCode()!=HttpStatus.OK) {
            return null;//BusinessException here
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
