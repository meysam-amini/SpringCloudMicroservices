package com.meysam.common.customsecurity.service.api;


import com.meysam.common.customsecurity.model.dto.AssignRolePermissionDto;
import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


public interface RolePermissionService {

    List<String> getPermissionsNames(List<Long> rolesIds);
    List<PermissionDTO> getPermissions(List<Long> rolesIds);
    void assignPermissionsToRole(AssignRolePermissionDto rolePermissionDto);
}
