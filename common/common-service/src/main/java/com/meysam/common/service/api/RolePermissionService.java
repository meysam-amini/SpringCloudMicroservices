package com.meysam.common.service.api;

import org.taba.common.model.model.dto.auth.PermissionDTO;
import org.taba.common.model.model.dto.backoffice.AssignRolePermissionDto;

import java.util.List;

public interface RolePermissionService {

    List<String> getPermissionsNames(List<Long> rolesIds);
    List<PermissionDTO> getPermissions(List<Long> rolesIds);
    void assignPermissionsToRole(AssignRolePermissionDto rolePermissionDto);

}
