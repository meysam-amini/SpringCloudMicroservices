package com.meysam.common.customsecurity.service.impl;


import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.dto.ProfileRoleDTO;
import com.meysam.common.customsecurity.model.dto.RoleDTO;
import com.meysam.common.customsecurity.model.entity.AdminRole;
import com.meysam.common.customsecurity.model.entity.Profile;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.repository.ProfileRepository;
import com.meysam.common.customsecurity.repository.AdminRoleRepository;
import com.meysam.common.customsecurity.service.api.AdminRoleService;
import com.meysam.common.customsecurity.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminRoleServiceImpl implements AdminRoleService {

    private final AdminRoleRepository adminRoleRepository;
    private final ProfileRepository adminRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    private final LocaleMessageSourceService messageSourceService;

    public AdminRole add(AdminRole adminRole){
        return adminRoleRepository.save(adminRole);
    }

    public List<Role> getRoles(Long profileId) {
        return adminRoleRepository.findRolesByProfileId(profileId);
    }

    public void save(ProfileRoleDTO profileRoleDTO) {
        adminRoleRepository.save(modelMapper.map(profileRoleDTO,AdminRole.class));
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleService.findAllRoles();
    }

    @Transactional
    @Override
    public void assignRolesToProfile(List<String> incomingRolesCodes, String username) {
        Profile profile = adminRepository.findByUsername(username).orElse(null);
        if(Objects.isNull(profile)){
            throw new BusinessException(messageSourceService.getMessage("PROFILE_NOT_FOUND"));
        }
        List<Role> currentRolesList = getRoles(profile.getId());
        List<String> currentRolesCodes = currentRolesList.stream().map(Role::getCode).toList();

        List<String> addedRolesCodes = new ArrayList<>();
        List<Role> deletedRolesList = new ArrayList<>();

        incomingRolesCodes.forEach(roleCode->{
            if(!currentRolesList.contains(roleCode)){
                addedRolesCodes.add(roleCode);
            }
        });
        currentRolesList.forEach(role->{
            if(!incomingRolesCodes.contains(role.getCode())){
                deletedRolesList.add(role);
            }
        });

        List<Long> deletedRolesIds = deletedRolesList.stream().map(Role::getId).collect(Collectors.toList());
        adminRoleRepository.deleteAllByIdIn(deletedRolesIds);

        addedRolesCodes.forEach(r->{

            Role role = roleService.findEntityByCode(r);
            if(Objects.isNull(role)){
                throw new BusinessException(messageSourceService.getMessage("ROLE_NOT_FOUND")+ " code:"+r);
            }

            if(adminRoleRepository.existsByAdminAndRole(profile.getId(),role.getId())){
                log.info("profile:{}, role:{} record already assigned",profile,role);
            }else {
                AdminRole profileRole = new AdminRole();
                profileRole.setAdmin(profile.getId());
                profileRole.setRole(role.getId());
//                profileRole.setIsActive(true);
//                profileRole.setCreatedAt(new Date());
//                profileRole.setCreatedBy(0L);
                adminRoleRepository.save(profileRole);
            }
        });
    }

    @Override
    public AdminRole findByProfileAndRole(long profile, long role) {
        return adminRoleRepository.findByAdminAndRole(profile,role).orElse(null);
    }
}
