package com.meysam.backoffice.service.auth.Impl;

import com.meysam.backoffice.service.auth.api.AdminAuthService;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.LoginResponseDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import org.springframework.http.ResponseEntity;

public class AdminAuthServiceImpl implements AdminAuthService {
    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity register(RegisterUserRequestDto registerUserRequestDto) {
        return null;
    }
}
