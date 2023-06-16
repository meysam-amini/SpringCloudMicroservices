package com.meysam.auth.service.api;

import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.LoginResponseDto;
import com.meysam.auth.model.dto.RegisterUserRequestDto;
import com.meysam.auth.model.dto.RegisterUserResponseDto;
import com.meysam.auth.model.entity.Role;
import com.meysam.common.model.entity.User;
import org.json.JSONObject;

import java.util.List;

public interface KeycloakService {

    List<Role> getRoles();

    JSONObject registerUser(RegisterUserRequestDto registerDto);

    LoginResponseDto loginUser(LoginRequestDto loginDto);

    Role getUserRole();

    User assignRole(User user);

}
