package com.meysam.backoffice.webapi.controller.members;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.LoginResponseDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.service.api.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDto> loginAdmin(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return adminAuthService.login(loginRequestDto);
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerNewAdmin(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        return adminAuthService.register(registerUserRequestDto);
    }

}
