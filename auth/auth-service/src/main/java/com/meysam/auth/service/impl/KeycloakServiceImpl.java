package com.meysam.auth.service.impl;

import com.meysam.auth.model.dto.ClientLoginResponseDto;
import com.meysam.auth.model.entity.Role;
import com.meysam.auth.model.enums.AuthGrantType;
import com.meysam.auth.model.enums.MemberLevel;
import com.meysam.auth.service.api.KeycloakService;
import com.meysam.common.configs.exception.KeycloakException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.model.dto.*;
import com.meysam.common.model.entity.Member;
import com.meysam.common.service.api.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    @Qualifier(value = "SimpleRestTemplate")
    @Autowired//for qualifier(lombok doesn't apply @Qualifier on constructor)
    private RestTemplate restTemplate;
    private final LocaleMessageSourceService messageSourceService;
    private final MemberService userService;


    @Value("${keycloak.get.token.url}")
    private String KEYCLOAK_GET_TOKEN_URL;
    @Value("${keycloak.register.user.url}")
    private String KEYCLOAK_REGISTER_USER_URL;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String CLIENT_ID;
    @Value("${oauth.client.secret}")
    private String CLIENT_SECRET;
    private String PASSWORD_GRANT_TYPE = "password";
    private String CLIENT_CREDENTIAL_GRANT_TYPE = "client_credentials";


    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public ResponseEntity registerUser(RegisterUserRequestDto registerDto, MemberLevel memberLevel) {
        String clientAccessToken = loginClient(CLIENT_ID, CLIENT_SECRET, AuthGrantType.client_credentials).getAccessToken();

        JSONObject passwordSchema = new JSONObject();
        passwordSchema.put("type", "password");
        passwordSchema.put("value", registerDto.getPassword());
        passwordSchema.put("temporary", false);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(passwordSchema);

        JSONObject userData = new JSONObject();

        userData.put("username", registerDto.getUsername());
        userData.put("credentials", jsonArray);
        userData.put("firstName", registerDto.getFirstName());
        userData.put("lastName", registerDto.getLastName());
        userData.put("email", registerDto.getEmail());
        userData.put("enabled", true);

        //assign group for role mapping:
        jsonArray = new JSONArray();
        jsonArray.put(memberLevel);
        userData.put("groups", jsonArray);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + clientAccessToken);

        System.err.println(userData.toString());
        HttpEntity<String> request = new HttpEntity<>(userData.toString(), headers);
        try {
            ResponseEntity<JSONObject> response = restTemplate.
                    postForEntity(KEYCLOAK_REGISTER_USER_URL, request, JSONObject.class);


            if (response.getStatusCode() == HttpStatus.CREATED) {
                saveUserIfNotExist(new MemberDto(registerDto.getUsername(), registerDto.getEmail(), registerDto.getFirstName(), registerDto.getLastName()));
                return ResponseEntity.status(HttpStatus.CREATED).body(messageSourceService.getMessage("REGISTER_SUCCESS"));
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }

        } catch (HttpClientErrorException e) {
            log.error("Exception on register new user on keycloak at keycloakServiceImpl at time :{}, exception is:{}", System.currentTimeMillis(), e);
            if(e.getStatusCode()==HttpStatus.CONFLICT){
                saveUserIfNotExist(new MemberDto(registerDto.getUsername(), registerDto.getEmail(), registerDto.getFirstName(), registerDto.getLastName()));
            }
            throw  new KeycloakException(e.getStatusCode(),e.getMessage());
        } catch (Exception e) {
            log.error("Exception on register new user on keycloak at keycloakServiceImpl at time :{}, exception is:{}", System.currentTimeMillis(), e);
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR,messageSourceService.getMessage("FAILED_TO_CONNECT_TO_KEYCLOAK"));
        }

    }


    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginDto) {

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("username", loginDto.getUsername());
        data.add("password", loginDto.getPassword());
        data.add("client_id", CLIENT_ID);
        data.add("client_secret", CLIENT_SECRET);
        data.add("grant_type", PASSWORD_GRANT_TYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data, headers);
        try {
            ResponseEntity<LoginResponseDto> response = restTemplate
                    .exchange(KEYCLOAK_GET_TOKEN_URL, HttpMethod.POST, request, LoginResponseDto.class);
            LoginResponseDto loginResponseDto = response.getBody();

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new KeycloakException(response.getStatusCode(),messageSourceService.getMessage("LOGIN_USER_FAILED"));
            } else {
                return loginResponseDto;
            }

        } catch (HttpClientErrorException e) {
            log.error("Exception on get token from keycloak at keycloakServiceImpl at time :{}, exception is:{}", System.currentTimeMillis(), e);
            throw  new KeycloakException(e.getStatusCode(),e.getMessage());
        } catch (Exception e) {
            log.error("Exception on get token from keycloak at keycloakServiceImpl at time :{}, exception is:{}", System.currentTimeMillis(), e);
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR,messageSourceService.getMessage("FAILED_TO_CONNECT_TO_KEYCLOAK"));
        }

    }

    @Override
    public ResponseEntity getTokenByRefreshToken(LoginRequestDto loginDto) {
        return null;
    }

    @Override
    public Role getUserRole(String username) {
        return null;
    }

    @Override
    public Member assignRole(Member user) {
        return null;
    }


    @Override
    public ResponseEntity loginClient(ClientLoginRequestDto loginDto) {
        return ResponseEntity.ok(loginClient(loginDto.getClientId(), loginDto.getClientSecret(), AuthGrantType.client_credentials));
    }

    @Override
    public ResponseEntity getClientRefreshToken(ClientLoginRequestDto loginDto) {
        return null;
    }


    private ClientLoginResponseDto loginClient(String clientId, String clientSecret, AuthGrantType grantType) {
        MultiValueMap<String, String> clientData = new LinkedMultiValueMap<>();
        clientData.add("client_id", clientId);
        clientData.add("client_secret", clientSecret);
        clientData.add("grant_type", grantType.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(clientData, headers);
        try {
            ResponseEntity<ClientLoginResponseDto> response = restTemplate
                    .exchange(KEYCLOAK_GET_TOKEN_URL, HttpMethod.POST, request, ClientLoginResponseDto.class);
            ClientLoginResponseDto loginResponseDto = response.getBody();

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new KeycloakException(response.getStatusCode(),messageSourceService.getMessage("LOGIN_CLIENT_FAILED"));
            } else {
                return loginResponseDto;
            }

        } catch (HttpClientErrorException e) {
            log.error("Exception on get client token from keycloak at keycloakServiceImpl at time :{}, exception is:{}", System.currentTimeMillis(), e);
            throw  new KeycloakException(e.getStatusCode(),e.getMessage());
        } catch (Exception e) {
            log.error("Exception on get client token from keycloak at keycloakServiceImpl at time :{}, exception is:{}", System.currentTimeMillis(), e);
            throw new KeycloakException(HttpStatus.INTERNAL_SERVER_ERROR,messageSourceService.getMessage("FAILED_TO_CONNECT_TO_KEYCLOAK"));
        }
    }

    private void saveUserIfNotExist(MemberDto userDto) {
        Member u = userService.findByUserName(userDto.getUsername());
        if (u == null) {
            Member user = Member.builder()
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .build();
            userService.createMember(user);
        }
    }
}
