package com.meysam.common.customsecurity.service.api;


import com.meysam.common.customsecurity.model.dto.ProfileRoleDTO;
import com.meysam.common.customsecurity.model.dto.RoleDTO;
import com.meysam.common.customsecurity.model.entity.AdminRole;
import com.meysam.common.customsecurity.model.entity.ProfileRole;
import com.meysam.common.customsecurity.model.entity.Role;

import java.util.List;


public interface AdminRoleService {


    public AdminRole add(AdminRole adminRole);

    public List<Role> getRoles(Long profileId);

    void save(ProfileRoleDTO profileRoleDTO);

    List<RoleDTO> getAllRoles();

    void assignRolesToProfile(List<String> roles, String username);

    AdminRole findByProfileAndRole(long profile , long role);
}
