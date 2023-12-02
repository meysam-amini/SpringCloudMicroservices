package com.meysam.backoffice.service.auth.api;

import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.LoginResponseDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import org.springframework.http.ResponseEntity;

public interface AdminAuthService {

    ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto);
    ResponseEntity register(RegisterUserRequestDto registerUserRequestDto);
}
