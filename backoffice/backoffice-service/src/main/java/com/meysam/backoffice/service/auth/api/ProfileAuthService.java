package com.meysam.backoffice.service.auth.api;

import com.meysam.common.customsecurity.model.dto.AdminLoginResponseDto;
import com.meysam.common.customsecurity.model.dto.RegisterAdminRequestDto;
import com.meysam.common.model.dto.*;
import org.springframework.http.ResponseEntity;

public interface ProfileAuthService {

    ResponseEntity<AdminLoginResponseDto> login(LoginRequestDto loginRequestDto);
    ResponseEntity<String> logout(String username);
    ResponseEntity register(RegisterAdminRequestDto registerAdminRequestDto);
}
