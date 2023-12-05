package com.meysam.backoffice.service.permission.api;

import com.meysam.backoffice.model.dto.AddPermissionDto;
import com.meysam.common.customsecurity.model.entity.Permission;

public interface PermissionService {
    Permission addPermission(AddPermissionDto permissionDto);
}
