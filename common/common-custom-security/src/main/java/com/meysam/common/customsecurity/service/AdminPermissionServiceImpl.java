package com.meysam.common.customsecurity.service;


import com.meysam.common.customsecurity.repository.AdminPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminPermissionServiceImpl {

    private final AdminPermissionRepository adminPermissionRepository;

    public List<String> getPermissions(Long adminId) {
        return adminPermissionRepository.findPermissionsNamesByAdmin(adminId);
    }
}
