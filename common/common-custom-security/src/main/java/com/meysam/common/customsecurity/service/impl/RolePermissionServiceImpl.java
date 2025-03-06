package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.exception.UnauthorizedException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.model.entity.RolePermission;
import com.meysam.common.customsecurity.repository.RolePermissionRepository;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.customsecurity.service.api.RolePermissionService;
import com.meysam.common.customsecurity.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignPermissionsToRole(SecurityPrinciple clientPrinciple, AssignRolePermissionDto rolePermissionDto) {
        Role role = roleService.findEntityId(rolePermissionDto.getRoleId())
                .orElseThrow(() -> new BusinessException(messageSourceService.getMessage("ROLE_NOT_FOUND")));

        if(clientPrinciple.getRoles().contains(role.getName())){
            throw new UnauthorizedException("YOU_CANT_MODIFY_YOUR_OWN_PERMISSIONS", HttpStatus.FORBIDDEN);
        }

        List<Permission> newPermissions = permissionService.findAllPermissionsByCodes(rolePermissionDto.getPermissionCodes());
        if (newPermissions==null || newPermissions.size()==0)
            newPermissions = new ArrayList<>();

        List<Permission> currentPermissionsByRole = rolePermissionRepository.findAllPermissionsByRole(role.getId());
        if(currentPermissionsByRole==null || currentPermissionsByRole.size()==0)
            currentPermissionsByRole = new ArrayList<>();

        List<Permission> revokedPermissions = new ArrayList<>(currentPermissionsByRole);
        revokedPermissions.removeAll(newPermissions);

        if (revokedPermissions.size() > 0)
            rolePermissionRepository.deleteAllByRoleAndPermissionIsIn(role.getId(), revokedPermissions);

        newPermissions.removeAll(currentPermissionsByRole);

        if (newPermissions.size() > 0) {
            newPermissions.forEach(p -> {

                Permission permission = permissionService.findEntityByCode(p.getCode());
                if (Objects.isNull(permission)) {
                    throw new BusinessException(messageSourceService.getMessage("PERMISSION_NOT_FOUND"));
                }

                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setPermission(permission.getId());
                    rolePermission.setRole(role.getId());
                    rolePermissionRepository.save(rolePermission);

            });
        }


    }

    @Override
    public void updatePermissionsByRole(AssignRolePermissionDto rolePermissionDto) {

    }
}