package com.meysam.auth.controller;

import com.meysam.auth.api.KeycloakService;
import com.meysam.auth.api.OtpService;
import com.meysam.auth.model.dto.*;
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


    @PostMapping("confirm-login")
    public ResponseEntity otpLogin(@RequestBody @Valid LoginRequestDto loginRequestDto,@RequestParam(value = "otpCode",required = true) long otpcode){
        otpService.validateOtpCode(loginRequestDto.getUsername(),loginRequestDto.getOtpTarget(), otpcode);
        LoginResponseDto loginResponseDto= keycloakService.loginUser(loginRequestDto);
        otpService.removeCachedOtpCodeAndWrongOtpCount(loginRequestDto.getUsername(),loginRequestDto.getOtpTarget());
        return ResponseEntity.ok(loginResponseDto);

    }

    @PostMapping("confirm-register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserRequestDto registerRequestDto, @RequestParam(value = "otpCOde",required = true) long otpcode){

        otpService.validateOtpCode(registerRequestDto.getUsername(),registerRequestDto.getOtpTarget(),otpcode);
        RegisterUserResponseDto registerUserResponseDto = keycloakService.registerUser(registerRequestDto);
        otpService.removeCachedOtpCodeAndWrongOtpCount(registerRequestDto.getUsername(),registerRequestDto.getOtpTarget());
        return ResponseEntity.ok(registerUserResponseDto);

    }

    @PostMapping("send-otp-to-registered-user")
    public ResponseEntity sendOtpForRegisteredUsers(@RequestBody @Valid SendOtpDto sendOtpDto){
        return otpService.sendOtp(sendOtpDto.getUsername(),sendOtpDto.getOtpTarget(),"",true);
    }

    @PostMapping("send-otp-for-register")
    public ResponseEntity sendOtpForNonRegisteredUsers(@RequestBody @Valid SendOtpDto sendOtpDto,@RequestParam(value = "destination",required = true) String destination){
        return otpService.sendOtp(sendOtpDto.getUsername(),sendOtpDto.getOtpTarget(),destination,false);
    }

}
