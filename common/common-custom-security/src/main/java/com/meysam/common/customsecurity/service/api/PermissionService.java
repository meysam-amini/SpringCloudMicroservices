package com.meysam.common.customsecurity.service.api;

import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.model.dto.AddPermissionDto;


public interface PermissionService {
    Permission addPermission(AddPermissionDto permissionDto);
}
