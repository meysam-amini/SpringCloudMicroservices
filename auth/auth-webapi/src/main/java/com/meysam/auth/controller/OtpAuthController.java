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
    public ResponseEntity otpLogin(@RequestBody @Valid LoginRequestDto loginRequestDto,@RequestParam(value = "otpCOde",required = true) long otpcode){
        otpService.validateOtpCode(loginRequestDto.getUsername(),loginRequestDto.getOtpTarget(), otpcode);
        return ResponseEntity.ok(keycloakService.loginUser(loginRequestDto));

    }

    @PostMapping("otp-register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserRequestDto registerRequestDto, @RequestParam(value = "otpCOde",required = true) long otpcode){

        otpService.validateOtpCode(registerRequestDto.getUsername(),registerRequestDto.getOtpTarget(),otpcode);
        return ResponseEntity.ok(keycloakService.registerUser(registerRequestDto));

    }


    @PostMapping("send-otp-login")
    public ResponseEntity loginForSendOtp(@RequestBody @Valid LoginRequestDto loginRequestDto){

        return null;
    }

    @PostMapping("send-otp-register")
    public ResponseEntity SendOtpForRegister(@RequestBody @Valid RegisterUserRequestDto registerUserRequestDto){
        //validate user inputs before sending otp

        Boolean sendOtpResult = otpService.sendOtp(registerUserRequestDto.getUsername(),registerUserRequestDto.getOtpTarget(),registerUserRequestDto.getOtpDestination());
        if(sendOtpResult){
            return ResponseEntity.ok().body(messageSourceService.getMessage("SEND_OTP_SUCCESS")+"to "+registerUserRequestDto.getOtpDestination());
        }else {
            return ResponseEntity.internalServerError().body(messageSourceService.getMessage("SEND_OTP_FAILED"));
        }
    }

    @PostMapping("send-otp-reset-password")
    public ResponseEntity loginForSendResetPassOtp(@RequestBody @Valid LoginRequestDto loginRequestDto){

        return null;
    }
}
