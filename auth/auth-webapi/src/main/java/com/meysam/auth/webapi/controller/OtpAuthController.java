package com.meysam.auth.webapi.controller;

import com.meysam.auth.service.api.KeycloakService;
import com.meysam.auth.service.api.OtpService;
import com.meysam.auth.model.dto.*;
import com.meysam.auth.model.enums.OtpTarget;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("otp-auth")
public class OtpAuthController {

    private final KeycloakService keycloakService;
    private final OtpService otpService;


    @PostMapping("confirm-login")
    public ResponseEntity otpLogin(@RequestBody @Valid LoginRequestDto loginRequestDto
            ,@RequestParam(value = "otpCode",required = true) long otpcode
            ,@RequestParam(value = "otpTarget",required = true) OtpTarget otpTarget){
        otpService.validateOtpCode(loginRequestDto.getUsername(),otpTarget, otpcode);
        LoginResponseDto loginResponseDto= keycloakService.loginUser(loginRequestDto);
        otpService.removeCachedOtpCodeAndWrongOtpCount(loginRequestDto.getUsername(),otpTarget);
        return ResponseEntity.ok(loginResponseDto);

    }

    @PostMapping("confirm-register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserRequestDto registerRequestDto
            , @RequestParam(value = "otpCOde",required = true) long otpcode
            , @RequestParam(value = "otpTarget",required = true) OtpTarget otpTarget){

        otpService.validateOtpCode(registerRequestDto.getUsername(),otpTarget,otpcode);
        JSONObject registerUserResponseDto = keycloakService.registerUser(registerRequestDto);
        otpService.removeCachedOtpCodeAndWrongOtpCount(registerRequestDto.getUsername(),otpTarget);
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
