package com.meysam.common.customsecurity.service.impl;


import com.meysam.common.customsecurity.model.dto.AddRoleDto;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Override
    public Role addRole(AddRoleDto permissionDto) {
        return null;
    }
}
