package com.meysam.backoffice.service.auth.Impl;

import com.meysam.backoffice.model.dto.AddPermissionDto;
import com.meysam.backoffice.service.auth.api.PermissionService;
import com.meysam.common.customsecurity.model.entity.Permission;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public Permission addPermission(AddPermissionDto permissionDto) {
        return null;
    }
}
