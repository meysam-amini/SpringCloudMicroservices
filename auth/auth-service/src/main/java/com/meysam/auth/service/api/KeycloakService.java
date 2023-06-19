package com.meysam.auth.service.api;

import com.meysam.auth.model.dto.*;
import com.meysam.auth.model.entity.Role;
import com.meysam.common.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KeycloakService {

    List<Role> getRoles();

    ResponseEntity registerUser(RegisterUserRequestDto registerDto);

    LoginResponseDto loginUser(LoginRequestDto loginDto);

    ClientLoginResponseDto loginClient(ClientLoginRequestDto loginDto);

    Role getUserRole();

    User assignRole(User user);

}
