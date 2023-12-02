package com.meysam.auth.webapi.controller;

import com.meysam.auth.model.enums.MemberLevel;
import com.meysam.auth.service.api.KeycloakService;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.LoginResponseDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth-admin")
public class AdminAuthController {

    private final KeycloakService keycloakService;

    //the admin side services could be in another separate microservice
    @PostMapping(value = "login-admin",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponseDto loginAdmin(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return keycloakService.loginUser(loginRequestDto);
    }

    @PostMapping("register-admin")
    public ResponseEntity registerAdmin(@RequestBody @Valid RegisterUserRequestDto registerRequestDto){
        return keycloakService.registerUser(registerRequestDto, MemberLevel.ADMIN);

    }

    @PostMapping("refresh-token")
    public ResponseEntity getRefreshToken(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(keycloakService.getTokenByRefreshToken(loginRequestDto));

    }

}
