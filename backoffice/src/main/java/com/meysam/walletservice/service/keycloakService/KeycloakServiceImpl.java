package com.meysam.walletservice.service.keycloakService;

import com.meysam.model.entity.Role;
import com.meysam.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakServiceImpl implements KeycloakService{
    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public User registerUser(User user) {
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
