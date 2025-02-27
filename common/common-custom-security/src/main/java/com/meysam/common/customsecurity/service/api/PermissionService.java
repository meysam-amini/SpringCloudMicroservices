package com.meysam.common.customsecurity.service.api;

import com.meysam.common.customsecurity.model.dto.AssignDirectPermissionDto;
import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.RolePermission;
import com.meysam.common.customsecurity.model.dto.AddPermissionDto;

import java.util.List;


public interface PermissionService {
    Permission findEntityByCode(String code);
    Permission addPermission(AddPermissionDto permissionDto);
    RolePermission assignPermissionToRole(AssignRolePermissionDto rolePermissionDto);
    String assignPermissionToUsername(AssignDirectPermissionDto permissionDto);
    List<Permission> getBasePermissions();
}
