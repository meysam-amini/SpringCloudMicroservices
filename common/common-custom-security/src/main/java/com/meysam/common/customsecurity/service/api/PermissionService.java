package com.meysam.common.customsecurity.service.api;

import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.RolePermission;
import com.meysam.common.customsecurity.model.dto.AddPermissionDto;

import java.util.List;


public interface PermissionService {
    Permission findEntityByCode(String code);
    Permission addPermission(AddPermissionDto permissionDto);
    List<Permission> getBasePermissions();
    List<Permission> findAllPermissionsByCodes(List<String> permissionCodes);
}
