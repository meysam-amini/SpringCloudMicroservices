package com.meysam.common.service.api;

import org.taba.common.model.model.dto.auth.AllRolePermissionsDTO;
import org.taba.common.model.model.dto.auth.PermissionDTO;
import org.taba.common.model.model.dto.auth.RolesPermissionsDTO;

import java.util.List;

public interface ProfilePermissionService {

    List<PermissionDTO> getAllRolePermissions(Long profileId);
    List<RolesPermissionsDTO> getAllRolePermissions();
    AllRolePermissionsDTO getAllRolePermissionsByProfile(long profileId);
    void assignPermissionsToProfile(List<String> permissions, String username);

}
