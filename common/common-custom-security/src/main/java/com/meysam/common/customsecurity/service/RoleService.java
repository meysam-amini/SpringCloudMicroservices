package com.meysam.common.customsecurity.service;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService  {

    private final RoleRepository repository;
    private final LocaleMessageSourceService messageSourceService;

    public Role findRoleByName(String name){
        return repository.findByName(name).orElseThrow(()->
                new BusinessException(messageSourceService.getMessage("ROLE_NOT_FOUND")));
    }
}
