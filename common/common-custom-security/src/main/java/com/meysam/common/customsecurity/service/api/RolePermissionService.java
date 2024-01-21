package com.meysam.common.customsecurity.service.api;


import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.model.entity.RolePermission;

public interface RolePermissionService {

    RolePermission assignPermissionToRole(AssignRolePermissionDto rolePermissionDto);

}
