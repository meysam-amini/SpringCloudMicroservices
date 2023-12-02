package com.meysam.common.customsecurity.service;


import org.springframework.stereotype.Service;
import org.taba.authsvc.models.dtos.RoleDTO;
import org.taba.authsvc.models.entity.Role;
import org.taba.authsvc.repositories.RoleRepository;
import org.taba.authsvc.utilities.mapper.RoleMapper;
import org.taba.common.service.abs.AbstractService;


@Service
public class RoleService extends AbstractService<RoleDTO, Role, RoleMapper, RoleRepository> {
    public RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

}
