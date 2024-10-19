package com.meysam.common.service.impl;


import com.meysam.common.service.api.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.taba.common.configs.exceptions.BusinessException;
import org.taba.common.configs.messages.LocaleMessageSourceService;
import org.taba.common.dao.repository.auth.PermissionRepository;
import org.taba.common.dao.repository.auth.RolePermissionRepository;
import org.taba.common.model.model.dto.backoffice.AddPermissionDto;
import org.taba.common.model.model.entity.auth.Permission;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final LocaleMessageSourceService messageSourceService;

    @Override
    public Permission addPermission(AddPermissionDto permissionDto) {
        if(permissionDto.getName().isEmpty()){
            throw new BusinessException(messageSourceService.getMessage("PERMISSION_NAME_IS_NULL"));
        }
        if(permissionDto.getCode().isEmpty()){
            throw new BusinessException(messageSourceService.getMessage("PERMISSION_CODE_IS_NULL"));
        }
        if(permissionRepository.existsByCodeOrName(permissionDto.getCode(),permissionDto.getName())){
            throw new BusinessException(messageSourceService.getMessage("PERMISSION_EXISTS"));
        }
        Permission permission = new Permission();
        permission.setCode(permissionDto.getCode());
        permission.setName(permissionDto.getName());
        return permissionRepository.save(permission);
    }

    @Override
    public Permission findEntityByCode(String code) {
        return permissionRepository.findByCode(code).orElse(null);
    }

}
