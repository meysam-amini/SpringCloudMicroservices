package com.meysam.walletservice.service.keycloakService;

import com.meysam.model.entity.Role;
import com.meysam.walletmanager.model.entity.User;

import java.util.List;

public interface KeycloakService {

    List<Role> getRoles();

    User registerUser(User user);

    Role getUserRole();

    User assignRole(User user);

}
