package com.meysam.auth.webapi.controller;

import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.service.api.KeycloakService;
import com.meysam.auth.model.dto.RegisterUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final KeycloakService keycloakService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto loginRequestDto){

        return ResponseEntity.ok(keycloakService.loginUser(loginRequestDto));

    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserRequestDto registerRequestDto){
        return ResponseEntity.ok(keycloakService.registerUser(registerRequestDto));

    }

}
