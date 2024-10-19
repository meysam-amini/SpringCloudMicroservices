package com.meysam.common.service.impl;


import com.meysam.common.service.api.PermissionService;
import com.meysam.common.service.api.ProfilePermissionService;
import com.meysam.common.service.api.RolePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.taba.common.configs.exceptions.BusinessException;
import org.taba.common.configs.messages.LocaleMessageSourceService;
import org.taba.common.dao.repository.auth.ProfilePermissionRepository;
import org.taba.common.dao.repository.auth.ProfileRepository;
import org.taba.common.model.model.dto.RoleDTO;
import org.taba.common.model.model.dto.auth.AllRolePermissionsDTO;
import org.taba.common.model.model.dto.auth.PermissionDTO;
import org.taba.common.model.model.dto.auth.RolesPermissionsDTO;
import org.taba.common.model.model.entity.auth.Permission;
import org.taba.common.model.model.entity.auth.Profile;
import org.taba.common.model.model.entity.auth.ProfilePermission;
import org.taba.common.model.model.entity.auth.Role;
import org.taba.common.service.auth.api.*;
import org.taba.common.util.mapper.auth.RoleMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfilePermissionServiceImpl implements ProfilePermissionService {

    private final PermissionService permissionService;
    private final LocaleMessageSourceService messageSourceService;
    private final ProfilePermissionRepository profilePermissionRepository;
    private final ProfileRepository profileRepository;
    private final ProfileRoleService profileRoleService;
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    private final RoleMapper roleMapper;



    @Override
    public List<PermissionDTO> getAllRolePermissions(Long profileId) {
        List<PermissionDTO> permissions = getProfilePermissions(profileId);
        List<Role> roles = profileRoleService.getRoles(profileId);
        if(!roles.isEmpty()){
            permissions.addAll(rolePermissionService.getPermissions(roles.stream().map(Role::getId).collect(Collectors.toList())));
        }
        return permissions;
    }

    @Override
    public AllRolePermissionsDTO getAllRolePermissionsByProfile(long profileId) {
        List<Role> roles = profileRoleService.getRoles(profileId);
        return AllRolePermissionsDTO.builder()
                .rolePermissions(getPermissions(roleMapper.toDtoList(roles)))
                .directPermissions(profilePermissionRepository.findAllPermissionsByProfile(profileId))
                .build();
    }

    @Override
    public List<RolesPermissionsDTO> getAllRolePermissions() {
        List<RoleDTO> roles = roleService.findAllRoles();
        return getPermissions(roles);
    }


    private List<RolesPermissionsDTO> getPermissions(List<RoleDTO> roles){
        return roles.parallelStream().map(role -> RolesPermissionsDTO.builder()
                .role(role)
                .permissions(rolePermissionService.getPermissions(Collections.singletonList(role.getId())))
                .build()).collect(Collectors.toList());
    }

    private List<PermissionDTO> getProfilePermissions(Long profileId) {
        return profilePermissionRepository.findAllPermissionsByProfile(profileId);
    }

    @Override
    public void assignPermissionsToProfile(List<String> incomingProfilePermissions, String username) {
        Profile profile = profileRepository.findByUsername(username).orElse(null);
        if(Objects.isNull(profile)){
            throw new BusinessException(messageSourceService.getMessage("PROFILE_NOT_FOUND"));
        }

        List<PermissionDTO> currentProfilePermissionsList = getProfilePermissions(profile.getId());
        List<String> currentPermissionsCodes = currentProfilePermissionsList.stream().map(PermissionDTO::getCode).toList();

        List<String> addedProfilePermissionsCodes = new ArrayList<>();
        List<PermissionDTO> deletedProfilePermissions = new ArrayList<>();

        incomingProfilePermissions.forEach(roleCode->{
            if(!currentPermissionsCodes.contains(roleCode)){
                addedProfilePermissionsCodes.add(roleCode);
            }
        });
        currentProfilePermissionsList.forEach(permission->{
            if(!incomingProfilePermissions.contains(permission.getCode())){
                deletedProfilePermissions.add(permission);
            }
        });

        List<Long> deletedProfilePermissionsIds = deletedProfilePermissions.stream().map(PermissionDTO::getId).toList();
        profilePermissionRepository.deleteAllByIdIn(deletedProfilePermissionsIds);
        addedProfilePermissionsCodes.forEach(p->{

            Permission permission = permissionService.findEntityByCode(p);
            if(Objects.isNull(permission)){
                throw new BusinessException(messageSourceService.getMessage("PERMISSION_NOT_FOUND")+" code: "+p);
            }

            if(profilePermissionRepository.existsByPermissionAndProfile(permission.getId(), profile.getId())){
                log.info("profile:{}, permission:{} record already assigned",profile,permission);
            }else {
                ProfilePermission profilePermission = new ProfilePermission();
                profilePermission.setPermission(permission.getId());
                profilePermission.setProfile(profile.getId());
                profilePermissionRepository.save(profilePermission);
            }
        });
    }
}
