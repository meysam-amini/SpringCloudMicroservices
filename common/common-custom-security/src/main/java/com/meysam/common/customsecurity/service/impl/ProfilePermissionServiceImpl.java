package com.meysam.common.customsecurity.service.impl;


import com.meysam.backoffice.model.dto.PermissionGroupDto;
import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.dto.AllRolePermissionsDTO;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.dto.RoleDTO;
import com.meysam.common.customsecurity.model.dto.RolesPermissionsDTO;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.repository.ProfileRepository;
import com.meysam.common.customsecurity.service.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProfilePermissionServiceImpl implements ProfilePermissionService {
    private final PermissionService permissionService;
    private final LocaleMessageSourceService messageSourceService;
    private final ProfileRepository profileRepository;
    private final ProfileRoleService profileRoleService;
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    private final ModelMapper modelMapper;
    private final RedisTemplate redisTemplate;


    @Override
    public List<PermissionDTO> getAllRolePermissions(Long profileId) {
        List<Role> roles = profileRoleService.getRoles(profileId);
        if (!roles.isEmpty()) {
            return rolePermissionService.getPermissions(roles.stream().map(Role::getId).collect(Collectors.toList()));
        }
        return new ArrayList<>();
    }

    @Override
    public List<PermissionGroupDto> getMappedPermissions(Long profileId, boolean inMenuCheck) {

        List<PermissionDTO> allPermissions = getAllRolePermissions(profileId);
        List<PermissionDTO> basePermissions = allPermissions
                .stream()
                .filter(permissionDTO -> permissionDTO.getParent() == null)
                .sorted(Comparator.comparing(PermissionDTO::getCode))
                .toList();

        /*try {
            ValueOperations<String, List<Permission>> valueOperations = redisTemplate.opsForValue();
            basePermissions = valueOperations.get(BASE_PERMISSIONS_KEY);
        } catch (Exception e) {
            log.error("Exception on connecting to Redis server for fetching base permissions at time:{} , exception is:{}", System.currentTimeMillis(), e);
            throw new BusinessException(messageSourceService.getMessage("EXCEPTION_IN_PROCESSING_PERMISSIONS_MAP"));
        }*/
        List<PermissionGroupDto> permissionGroupDtos = new ArrayList<>();

        try {
            Map<Long, List<PermissionDTO>> permissions = allPermissions.stream()
                    .filter(permissionDTO -> permissionDTO.getParent() != null)
                    .filter(permissionDTO -> {
                                if (inMenuCheck)
                                    return permissionDTO.isInMenu() == inMenuCheck;
                                else return true;
                            }
                    ).collect(Collectors.groupingBy(PermissionDTO::getParent));

            for (PermissionDTO basePermission : basePermissions) {
                PermissionGroupDto dto = PermissionGroupDto.builder()
                        .name(basePermission.getName())
                        .build();
                if (permissions.get(basePermission.getId()) != null)
                    dto.setSubGroups(permissions.get(basePermission.getId()).stream().map(PermissionDTO::getName).collect(Collectors.toList()));

                permissionGroupDtos.add(dto);
            }

        } catch (Exception e) {
            log.error("Exception in processing permissions map at time:{}, exception:{}", System.currentTimeMillis(), e);
            throw new BusinessException(messageSourceService.getMessage("EXCEPTION_IN_PROCESSING_PERMISSIONS_MAP"));
        }

        return permissionGroupDtos;

    }

    @Override
    public List<RolesPermissionsDTO> getAllRolePermissionsByProfile(long profileId) {
        List<Role> roles = profileRoleService.getRoles(profileId);
        return getPermissions(roles.stream().map(role -> modelMapper.map(role, RoleDTO.class)).collect(Collectors.toList()));
    }

    @Override
    public List<RolesPermissionsDTO> getAllRolePermissions() {
        List<RoleDTO> roles = roleService.findAllRoles();
        return getPermissions(roles);
    }


    private List<RolesPermissionsDTO> getPermissions(List<RoleDTO> roles) {
        return roles.parallelStream().map(role -> RolesPermissionsDTO.builder()
                .role(role)
                .permissions(rolePermissionService.getPermissions(Collections.singletonList(role.getId())))
                .build()).collect(Collectors.toList());
    }
}