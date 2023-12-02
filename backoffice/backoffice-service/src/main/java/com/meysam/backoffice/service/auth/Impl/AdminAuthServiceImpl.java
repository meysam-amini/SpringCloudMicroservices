package com.meysam.backoffice.service.auth.Impl;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.LoginResponseDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.service.api.AdminAuthServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final LocaleMessageSourceService messageSourceService;
    private final AdminAuthServiceClient authServiceClient;

    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        //check for admin existance in db, if wasn't present
        return authServiceClient.adminLogin(loginRequestDto);
    }

    @Override
    public ResponseEntity register(RegisterUserRequestDto registerUserRequestDto) {
        //insert admin to Admin table after successful register
        return authServiceClient.registerAdmin(registerUserRequestDto);
    }
}
