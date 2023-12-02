package com.meysam.common.customsecurity.service;


import com.meysam.common.customsecurity.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    public List<String> getPermissions(List<BigInteger> rolesIds) {
        return rolePermissionRepository.findAllPermissionsNamesByRoles(rolesIds);
    }
}
