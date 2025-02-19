package com.meysam.auth.service.api;

import com.meysam.auth.model.enums.MemberLevel;
import com.meysam.common.model.dto.ClientLoginRequestDto;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.LoginResponseDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import org.springframework.http.ResponseEntity;


public interface KeycloakService {

    ResponseEntity registerUser(RegisterUserRequestDto registerDto, MemberLevel memberLevel);

    LoginResponseDto loginUser(LoginRequestDto loginDto);

    ResponseEntity getTokenByRefreshToken(LoginRequestDto loginDto);

    ResponseEntity loginClient(ClientLoginRequestDto loginDto);

    ResponseEntity getClientRefreshToken(ClientLoginRequestDto loginDto);

}
