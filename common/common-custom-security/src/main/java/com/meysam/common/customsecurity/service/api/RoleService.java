package com.meysam.common.customsecurity.service.api;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.dto.AddRoleDto;
import com.meysam.common.customsecurity.model.dto.BackofficeRoleDTO;
import com.meysam.common.customsecurity.model.dto.RoleDTO;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface RoleService  {

    public Role findRoleByName(String name);
    RoleDTO findByCode(String code);
    List<RoleDTO> findAllRoles();
    List<BackofficeRoleDTO> findAllRolesForBackoffice();
    Role findEntityByCode(String code);
    Role addRole(AddRoleDto addRoleDto);
    Role getRoleEntityByCode(String code);

}
