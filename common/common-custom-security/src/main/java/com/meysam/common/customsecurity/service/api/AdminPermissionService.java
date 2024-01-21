package com.meysam.common.customsecurity.service.api;


import com.meysam.common.customsecurity.model.dto.AssignDirectPermissionDto;

public interface AdminPermissionService {

    String assignPermissionToUsername(AssignDirectPermissionDto permissionDto);

}
