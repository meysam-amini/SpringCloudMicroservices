package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.repository.PermissionRepository;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.model.dto.AddPermissionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission addPermission(AddPermissionDto permissionDto) {
        return null;
    }
}
