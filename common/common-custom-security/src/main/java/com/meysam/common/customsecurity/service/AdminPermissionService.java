package com.meysam.common.customsecurity.service;


import com.meysam.common.customsecurity.repository.AdminPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminPermissionService {

    private final AdminPermissionRepository adminPermissionRepository;

    public List<String> getPermissions(BigInteger adminId) {
        return adminPermissionRepository.findPermissionsNamesByAdmin(adminId);
    }
}
