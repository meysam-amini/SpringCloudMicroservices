package com.meysam.auth.service.api;

import com.meysam.auth.model.dto.*;
import com.meysam.auth.model.entity.Role;
import com.meysam.common.model.dto.ClientLoginRequestDto;
import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.model.entity.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KeycloakService {

    List<Role> getRoles();

    ResponseEntity registerUser(RegisterUserRequestDto registerDto);

    LoginResponseDto loginUser(LoginRequestDto loginDto);
    LoginResponseDto getUserRefreshToken(LoginRequestDto loginDto);

    ClientLoginResponseDto loginClient(ClientLoginRequestDto loginDto);
    ClientLoginResponseDto getClientRefreshToken(ClientLoginRequestDto loginDto);


    Role getUserRole(String username);

    Member assignRole(Member user);

}
