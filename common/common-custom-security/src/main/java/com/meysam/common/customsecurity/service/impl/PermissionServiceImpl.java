package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.RolePermission;
import com.meysam.common.customsecurity.repository.PermissionRepository;
import com.meysam.common.customsecurity.service.api.PermissionService;
import com.meysam.common.customsecurity.model.dto.AddPermissionDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final LocaleMessageSourceService messageSourceService;


    @Override
    public Permission findEntityByCode(String code) {
        return permissionRepository.findByCode(code).orElse(null);
    }

    @Transactional
    @Override
    public Permission addPermission(AddPermissionDto permissionDto) {
        if(permissionDto.getName().isEmpty()){
            throw new BusinessException(messageSourceService.getMessage("PERMISSION_NAME_IS_NULL"));
        }
        if(permissionDto.getCode().isEmpty()){
            throw new BusinessException(messageSourceService.getMessage("PERMISSION_CODE_IS_NULL"));
        }
        Permission permission = new Permission();
        permission.setCode(permissionDto.getCode());
        permission.setName(permissionDto.getName());
        return permissionRepository.save(permission);
    }


    @Override
    public List<Permission> getBasePermissions() {
        List<Permission> permissions = permissionRepository.findAllByParentIsNullAndEnabledIsTrue()
                .stream()
                .sorted(Comparator.comparing(Permission::getCode))
                .toList();
        return permissions;
    }

    @Override
    public List<Permission> findAllPermissionsByCodes(List<String> permissionCodes) {
        return permissionRepository.findPermissionsByCodeIsIn(permissionCodes);
    }
}
