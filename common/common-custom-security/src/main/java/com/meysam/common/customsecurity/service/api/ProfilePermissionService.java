package com.meysam.common.customsecurity.service.api;


import com.meysam.backoffice.model.dto.PermissionGroupDto;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.dto.RolesPermissionsDTO;

import java.util.List;

public interface ProfilePermissionService {

    List<PermissionDTO> getAllRolePermissions(Long profileId);

    List<PermissionGroupDto> getMappedPermissions(Long profileId,boolean inMenuCheck);

    List<RolesPermissionsDTO> getAllRolePermissions();

    List<RolesPermissionsDTO> getAllRolePermissionsByProfile(long profileId);
}
