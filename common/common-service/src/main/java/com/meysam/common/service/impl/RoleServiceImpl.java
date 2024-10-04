package com.meysam.common.service.impl;

import com.meysam.common.service.api.RoleService;
import org.springframework.stereotype.Service;
import org.taba.common.configs.exceptions.BusinessException;
import org.taba.common.dao.repository.auth.RoleRepository;
import org.taba.common.model.model.dto.RoleDTO;
import org.taba.common.model.model.dto.backoffice.AddRoleDto;
import org.taba.common.model.model.dto.backoffice.BackofficeRoleDTO;
import org.taba.common.model.model.entity.auth.Role;
import org.taba.common.util.mapper.auth.RoleMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO findByCode(String code) {
        Optional<Role> byCode = roleRepository.findByCode(code);
        if (byCode.isEmpty())
            throw new BusinessException("role not found");

        return roleMapper.toDto(byCode.get());
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        RoleDTO result;
        List<RoleDTO> results = new ArrayList<>();
        List<Role> foundItems = roleRepository.findAll();
        for(Role item : foundItems){
            result = roleMapper.toDto(item);
            results.add(result);
        }
        return results;
    }

    @Override
    public List<BackofficeRoleDTO> findAllRolesForBackoffice() {
        return roleRepository.findAll().stream().map(role -> {
            BackofficeRoleDTO dto = BackofficeRoleDTO.builder()
                    .enKey(role.getEnKey())
                    .code(role.getCode())
                    .name(role.getName())
                    .build();
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Role findEntityByCode(String code) {
        return roleRepository.findByCode(code).orElse(null);
    }

    @Override
    public Role addRole(AddRoleDto addRoleDto) {
        return null;
    }

    @Override
    public Role getRoleEntityByCode(String code) {
        return roleRepository.findByCode(code).orElse(null);
    }
}
