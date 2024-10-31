package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.model.entity.RolePermission;
import com.meysam.common.customsecurity.repository.RolePermissionRepository;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.customsecurity.service.api.ProfileRoleService;
import com.meysam.common.customsecurity.service.api.RolePermissionService;
import com.meysam.common.customsecurity.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final LocaleMessageSourceService messageSourceService;


    @Override
    public List<String> getPermissionsNames(List<Long> rolesIds) {
        return rolePermissionRepository.findAllPermissionsNamesByRoles(rolesIds);
    }

    @Override
    public List<PermissionDTO> getPermissions(List<Long> rolesIds) {
        return rolePermissionRepository.findAllPermissionsByRoles(rolesIds);
    }

    @Override
    public void assignPermissionsToRole(AssignRolePermissionDto rolePermissionDto) {
        Role role = roleService.findEntityByCode(rolePermissionDto.getRoleCode());
        if (Objects.isNull(role)) {
            throw new BusinessException(messageSourceService.getMessage("ROLE_NOT_FOUND"));
        }
        rolePermissionDto.getPermissions().forEach(p -> {

            Permission permission = permissionService.findEntityByCode(p);
            if (Objects.isNull(permission)) {
                throw new BusinessException(messageSourceService.getMessage("PERMISSION_NOT_FOUND"));
            }

            if (rolePermissionRepository.existsByRoleAndPermission(role.getId(), permission.getId())) {
                throw new BusinessException(messageSourceService.getMessage("PROFILE_PERMISSION_EXISTS"));
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permission.getId());
            rolePermission.setRole(role.getId());
            rolePermissionRepository.save(rolePermission);
        });
    }
}