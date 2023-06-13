package com.meysam.webapi.controller;

import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.LoginResponseDto;
import com.meysam.auth.service.api.KeycloakService;
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

        LoginResponseDto responseDto = keycloakService.loginUser(loginRequestDto);

        return ResponseEntity.ok(responseDto);
    }
}
