package com.meysam.common.customsecurity.service.impl;


import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.dto.AllRolePermissionsDTO;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.dto.RoleDTO;
import com.meysam.common.customsecurity.model.dto.RolesPermissionsDTO;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.Profile;
import com.meysam.common.customsecurity.model.entity.ProfilePermission;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.repository.ProfilePermissionRepository;
import com.meysam.common.customsecurity.repository.ProfileRepository;
import com.meysam.common.customsecurity.service.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.meysam.common.model.constants.GlobalConstants.BASE_PERMISSIONS_KEY;


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
    private final ModelMapper roleMapper;
    private final RedisTemplate redisTemplate;


    @Override
    public List<PermissionDTO> getAllRolePermissions(Long profileId) {
        List<PermissionDTO> permissions = getProfilePermissions(profileId);
        List<Role> roles = profileRoleService.getRoles(profileId);
        if (!roles.isEmpty()) {
            permissions.addAll(rolePermissionService.getPermissions(roles.stream().map(Role::getId).collect(Collectors.toList())));
        }
        return permissions;
    }

    @Override
    public Map<String, List<String>> getMappedPermissions(Long profileId) {
        List<PermissionDTO> permissions = getAllRolePermissions(profileId);
        HashMap<Long, Permission> basePermissionsMap;
        try {
            ValueOperations<String, HashMap<Long, Permission>> valueOperations = redisTemplate.opsForValue();
            basePermissionsMap = valueOperations.get(BASE_PERMISSIONS_KEY);
        } catch (Exception e) {
            log.error("Exception on connecting to Redis server for refreshing base permissions cache at time:{} , exception is:{}", System.currentTimeMillis(), e);
            throw new BusinessException(messageSourceService.getMessage("SERVER_ERROR_REFRESHING_PERMISSIONS_CACHE"));
        }
        HashMap<String, List<String>> permissionsMap;

        Map<String, List<String>> groupedByPermissionsNames = new HashMap<>();

        try {
            permissions.forEach(permissionDTO -> {

                String basePermission = basePermissionsMap.get(permissionDTO.getParent()).getName();

                if (!groupedByPermissionsNames.containsKey(basePermission)) {
                    groupedByPermissionsNames.put(basePermission, Collections.singletonList(permissionDTO.getName()));
                } else {
                    groupedByPermissionsNames.get(basePermission).add(permissionDTO.getName());
                }
            });
        }catch (Exception e){
            log.error("Exception in processing permissions map at time:{}, exception:{}",System.currentTimeMillis(),e);
            throw new BusinessException(messageSourceService.getMessage("EXCEPTION_IN_PROCESSING_PERMISSIONS_MAP"));
        }

        return groupedByPermissionsNames;

    }

    @Override
    public AllRolePermissionsDTO getAllRolePermissionsByProfile(long profileId) {
        List<Role> roles = profileRoleService.getRoles(profileId);
        return AllRolePermissionsDTO.builder()
                .rolePermissions(getPermissions(roles.stream().map(role -> roleMapper.map(role, RoleDTO.class)).collect(Collectors.toList())))
                .directPermissions(profilePermissionRepository.findAllPermissionsByProfile(profileId))
                .build();
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

    private List<PermissionDTO> getProfilePermissions(Long profileId) {
        return profilePermissionRepository.findAllPermissionsByProfile(profileId);
    }

    @Override
    public void assignPermissionsToProfile(List<String> incomingProfilePermissions, String username) {
        Profile profile = profileRepository.findByUsername(username).orElse(null);
        if (Objects.isNull(profile)) {
            throw new BusinessException(messageSourceService.getMessage("PROFILE_NOT_FOUND"));
        }

        List<PermissionDTO> currentProfilePermissionsList = getProfilePermissions(profile.getId());
        List<String> currentPermissionsCodes = currentProfilePermissionsList.stream().map(PermissionDTO::getCode).toList();

        List<String> addedProfilePermissionsCodes = new ArrayList<>();
        List<PermissionDTO> deletedProfilePermissions = new ArrayList<>();

        incomingProfilePermissions.forEach(roleCode -> {
            if (!currentPermissionsCodes.contains(roleCode)) {
                addedProfilePermissionsCodes.add(roleCode);
            }
        });
        currentProfilePermissionsList.forEach(permission -> {
            if (!incomingProfilePermissions.contains(permission.getCode())) {
                deletedProfilePermissions.add(permission);
            }
        });

        List<Long> deletedProfilePermissionsIds = deletedProfilePermissions.stream().map(PermissionDTO::getId).toList();
        profilePermissionRepository.deleteAllByIdIn(deletedProfilePermissionsIds);
        addedProfilePermissionsCodes.forEach(p -> {

            Permission permission = permissionService.findEntityByCode(p);
            if (Objects.isNull(permission)) {
                throw new BusinessException(messageSourceService.getMessage("PERMISSION_NOT_FOUND") + " code: " + p);
            }

            if (profilePermissionRepository.existsByPermissionAndProfile(permission.getId(), profile.getId())) {
                log.info("profile:{}, permission:{} record already assigned", profile, permission);
            } else {
                ProfilePermission profilePermission = new ProfilePermission();
                profilePermission.setPermission(permission.getId());
                profilePermission.setProfile(profile.getId());
                profilePermissionRepository.save(profilePermission);
            }
        });
    }
}