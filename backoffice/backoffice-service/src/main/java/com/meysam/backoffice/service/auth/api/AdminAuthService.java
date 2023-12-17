package com.meysam.backoffice.service.auth.api;

import com.meysam.common.model.dto.*;
import org.springframework.http.ResponseEntity;

public interface AdminAuthService {

    ResponseEntity<AdminLoginResponseDto> login(LoginRequestDto loginRequestDto);
    ResponseEntity<String> logout(String username);
    ResponseEntity register(RegisterAdminRequestDto registerAdminRequestDto);
}
