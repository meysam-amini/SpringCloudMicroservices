package com.meysam.auth.webapi.controller;


import com.meysam.auth.model.dto.ClientLoginRequestDto;
import com.meysam.auth.service.api.KeycloakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth-client")
public class ClientsAuthController {

    private final KeycloakService keycloakService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid ClientLoginRequestDto loginRequestDto){
        return ResponseEntity.ok(keycloakService.loginClient(loginRequestDto));
    }
}
