package com.meysam.common.customsecurity.service.api;

import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.RolePermission;
import com.meysam.common.customsecurity.model.dto.AddPermissionDto;


public interface PermissionService {
    Permission addPermission(AddPermissionDto permissionDto);
    RolePermission assignPermissionToRole(AddPermissionDto permissionDto);
}
