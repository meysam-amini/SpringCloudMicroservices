package com.meysam.common.customsecurity.service.api;


import com.meysam.common.customsecurity.model.dto.AddRoleDto;
import com.meysam.common.customsecurity.model.entity.Role;

public interface RoleService {

    Role addRole(AddRoleDto permissionDto);

}
