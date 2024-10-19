package com.meysam.common.service.api;

import org.taba.common.model.model.dto.RoleDTO;
import org.taba.common.model.model.dto.backoffice.AddRoleDto;
import org.taba.common.model.model.dto.backoffice.BackofficeRoleDTO;
import org.taba.common.model.model.entity.auth.Role;

import java.util.List;

public interface RoleService {

    RoleDTO findByCode(String code);
    List<RoleDTO> findAllRoles();
    List<BackofficeRoleDTO> findAllRolesForBackoffice();
    Role findEntityByCode(String code);
    Role addRole(AddRoleDto addRoleDto);
    Role getRoleEntityByCode(String code);

}
