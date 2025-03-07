package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.dto.AddRoleDto;
import com.meysam.common.customsecurity.model.dto.BackofficeRoleDTO;
import com.meysam.common.customsecurity.model.dto.RoleDTO;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.repository.RoleRepository;
import com.meysam.common.customsecurity.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final LocaleMessageSourceService messageSourceService;

    public Role findRoleByName(String name){
        return roleRepository.findByName(name).orElseThrow(()->
                new BusinessException(messageSourceService.getMessage("ROLE_NOT_FOUND")));
    }

    @Override
    public RoleDTO findByCode(String code) {
        Optional<Role> byCode = roleRepository.findByCode(code);
        if (byCode.isEmpty())
            throw new BusinessException("role not found");

        return modelMapper.map(byCode.get(),RoleDTO.class);
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        RoleDTO result;
        List<RoleDTO> results = new ArrayList<>();
        List<Role> foundItems = roleRepository.findAll();
        for(Role item : foundItems){
            result = modelMapper.map(item,RoleDTO.class);
            results.add(result);
        }
        return results;
    }

    @Override
    public List<BackofficeRoleDTO> findAllRolesForBackoffice() {
        return roleRepository.findAll().stream().map(role -> {
            BackofficeRoleDTO dto = BackofficeRoleDTO.builder()
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
    public Optional<Role> findEntityId(long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role addRole(AddRoleDto addRoleDto) {
        if(Objects.isNull(addRoleDto)){
            throw new BusinessException(messageSourceService.getMessage("ROLE_OBJECT_NULL"));
        }
        if(isNullOrBlank(addRoleDto.getName())||isNullOrBlank(addRoleDto.getCode())){
            throw new BusinessException(messageSourceService.getMessage("ROLE_NAME_OR_CODE_NULL"));
        }
        if(roleRepository.existsByNameOrCode(addRoleDto.getName(),addRoleDto.getCode())){
            throw new BusinessException(messageSourceService.getMessage("ROLE_ALREADY_EXISTS_BY_NAME_OR_ROLE"));
        }

        Role role = Role.builder()
                .code(addRoleDto.getCode())
                .name(addRoleDto.getName())
                .createdDate(new Date())
                .updatedDate(new Date())
                .version(1L)
                .enabled(true)
                .build();

        return roleRepository.save(role);
    }

    @Override
    public Role getRoleEntityByCode(String code) {
        return roleRepository.findByCode(code).orElse(null);
    }


    private boolean isNullOrBlank(String s){
        return s.isBlank() || s.isEmpty();
    }
}
