package com.meysam.auth.service.impl;

import com.meysam.auth.service.api.KeycloakService;
import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.LoginResponseDto;
import com.meysam.auth.model.dto.RegisterUserRequestDto;
import com.meysam.auth.model.dto.RegisterUserResponseDto;
import com.meysam.auth.model.entity.Role;
import com.meysam.common.model.entity.User;
import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
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
    private String PASSWORD_GRANT_TYPE="password";
    private String CLIENT_CRIDENTIAL_GRANT_TYPE="client_credentials";

    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerDto) {
        String clientAccessToken = fetchClientAccessToken();
        MultiValueMap<String, Object> userData = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> passwordSchema = new LinkedMultiValueMap<>();
        passwordSchema.add("type","password");
        passwordSchema.add("value",registerDto.getPassword());
        passwordSchema.add("temporary","false");

        userData.add("username",registerDto.getUsername());
        userData.add("password",passwordSchema);
        userData.add("firstName",registerDto.getFirstName());
        userData.add("lastName",registerDto.getLastName());
        userData.add("email",registerDto.getEmail());
        userData.add("enabled",true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Bearer "+clientAccessToken);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(userData,headers);
        try {
            ResponseEntity<RegisterUserResponseDto> response = restTemplate
                    .exchange(KEYCLOAK_GET_TOKEN_URL, HttpMethod.POST, request, RegisterUserResponseDto.class);
            RegisterUserResponseDto registerUserResponseDto = response.getBody();

            if(response.getStatusCode()!=HttpStatus.OK) {
                throw new BusinessException(messageSourceService.getMessage("REGISTER_USER_FAILED"));
            }else {
                return registerUserResponseDto;
            }

        }catch (Exception e){
            log.error("Exception on register new user on keycloak at keycloakServiceImpl at time :{}, exception is:{}",System.currentTimeMillis(),e);
            throw new BusinessException(messageSourceService.getMessage("FAILED_TO_CONNECT_TO_KEYCLOAK"));
        }

    }


    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginDto) {

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("username",loginDto.getUsername());
        data.add("password",loginDto.getPassword());
        data.add("client_id",CLIENT_ID);
        data.add("client_secret",CLIENT_SECRET);
        data.add("grant_type",PASSWORD_GRANT_TYPE);

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


    private String fetchClientAccessToken() {
        MultiValueMap<String, String> clientData = new LinkedMultiValueMap<>();
        clientData.add("client_id",CLIENT_ID);
        clientData.add("client_secret",CLIENT_SECRET);
        clientData.add("grant_type",CLIENT_CRIDENTIAL_GRANT_TYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(clientData,headers);
        try {
            ResponseEntity<LoginResponseDto> response = restTemplate
                    .exchange(KEYCLOAK_GET_TOKEN_URL, HttpMethod.POST, request, LoginResponseDto.class);
            LoginResponseDto loginResponseDto = response.getBody();

            if(response.getStatusCode()!=HttpStatus.OK) {
                throw new BusinessException(messageSourceService.getMessage("LOGIN_CLIENT_FAILED"));
            }else {
                return loginResponseDto.getAccessToken();
            }

        }catch (Exception e){
            log.error("Exception on get client token from keycloak at keycloakServiceImpl at time :{}, exception is:{}",System.currentTimeMillis(),e);
            throw new BusinessException(messageSourceService.getMessage("FAILED_TO_CONNECT_TO_KEYCLOAK"));
        }
    }

}