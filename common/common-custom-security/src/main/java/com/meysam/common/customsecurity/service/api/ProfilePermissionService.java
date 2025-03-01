package com.meysam.common.customsecurity.service.api;


import com.meysam.backoffice.model.dto.PermissionGroupDto;
import com.meysam.common.customsecurity.model.dto.AllRolePermissionsDTO;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.dto.RolesPermissionsDTO;

import java.util.List;

public interface ProfilePermissionService {

    //    String assignPermissionToUsername(AssignDirectPermissionDto permissionDto);
    List<PermissionDTO> getAllRolePermissions(Long profileId);

    List<PermissionGroupDto> getMappedPermissions(Long profileId);

    List<RolesPermissionsDTO> getAllRolePermissions();

    AllRolePermissionsDTO getAllRolePermissionsByProfile(long profileId);

    void assignPermissionsToProfile(List<String> permissions, String username);


}
