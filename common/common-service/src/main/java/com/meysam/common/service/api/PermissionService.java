package com.meysam.common.service.api;


import org.taba.common.model.model.dto.backoffice.AddPermissionDto;
import org.taba.common.model.model.entity.auth.Permission;

public interface PermissionService {
    Permission addPermission(AddPermissionDto permissionDto);
    Permission findEntityByCode(String code);
}
