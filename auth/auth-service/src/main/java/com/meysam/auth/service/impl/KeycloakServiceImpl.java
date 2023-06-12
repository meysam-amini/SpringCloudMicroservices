package com.meysam.auth.service.impl;

import com.meysam.auth.model.dto.LoginRequestDto;
import com.meysam.auth.model.dto.LoginResponseDto;
import com.meysam.auth.model.entity.Role;
import com.meysam.auth.model.entity.User;
import com.meysam.auth.service.api.KeycloakService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public User registerUser(User user) {
        return null;
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginDto) {
        return null;
    }

    @Override
    public Role getUserRole() {
        return null;
    }

    @Override
    public User assignRole(User user) {
        return null;
    }
}
