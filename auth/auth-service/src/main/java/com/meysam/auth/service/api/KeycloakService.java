package com.meysam.auth.service.api;

import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.LoginResponseDto;
import com.meysam.auth.model.dto.RegisterUserRequestDto;
import com.meysam.auth.model.entity.Role;
import com.meysam.auth.model.entity.User;

import java.util.List;

public interface KeycloakService {

    List<Role> getRoles();

    User registerUser(RegisterUserRequestDto registerDto);

    LoginResponseDto loginUser(LoginRequestDto loginDto);

    Role getUserRole();

    User assignRole(User user);

}
