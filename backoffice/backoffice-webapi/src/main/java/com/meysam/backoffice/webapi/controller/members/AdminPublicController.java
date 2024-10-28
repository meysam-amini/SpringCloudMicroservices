package com.meysam.backoffice.webapi.controller.members;

import com.meysam.backoffice.service.auth.api.ProfileAuthService;
import com.meysam.common.customsecurity.model.dto.AdminLoginResponseDto;
import com.meysam.common.model.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AdminPublicController {

    private final ProfileAuthService adminAuthService;

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminLoginResponseDto> loginAdmin(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return adminAuthService.login(loginRequestDto);
    }


}
