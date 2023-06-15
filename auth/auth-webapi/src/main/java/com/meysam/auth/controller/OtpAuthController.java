package com.meysam.auth.controller;

import com.meysam.auth.api.KeycloakService;
import com.meysam.auth.api.OtpService;
import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.RegisterUserRequestDto;
import com.meysam.common.messages.LocaleMessageSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("otp-auth")
public class OtpAuthController {

    private final KeycloakService keycloakService;
    private final OtpService otpService;
    private final LocaleMessageSourceService messageSourceService;

    @PostMapping("otp-login")
    public ResponseEntity otpLogin(@RequestBody @Valid LoginRequestDto loginRequestDto){

        return ResponseEntity.ok(keycloakService.loginUser(loginRequestDto));

    }

    @PostMapping("otp-register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserRequestDto registerRequestDto, @RequestParam("otpCOde") long otpcode){

        if(otpService.isValidOtpCode(registerRequestDto.getUsername(),registerRequestDto.getOtpTarget(),otpcode)) {
            return ResponseEntity.ok(keycloakService.registerUser(registerRequestDto));
        }else {
            return ResponseEntity.badRequest().body(messageSourceService.getMessage("WRONG_OTP_CODE"));
        }

    }


    @PostMapping("send-otp-login")
    public ResponseEntity loginForSendOtp(@RequestBody @Valid LoginRequestDto loginRequestDto){

        return null;
    }

    @PostMapping("send-otp-register")
    public ResponseEntity SendOtpForRegister(@RequestBody @Valid RegisterUserRequestDto registerUserRequestDto){

        return null;
    }

    @PostMapping("send-otp-reset-password")
    public ResponseEntity loginForSendResetPassOtp(@RequestBody @Valid LoginRequestDto loginRequestDto){

        return null;
    }
}
