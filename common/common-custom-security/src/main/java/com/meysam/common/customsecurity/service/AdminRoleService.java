package com.meysam.common.customsecurity.service;


import com.meysam.common.customsecurity.model.entity.AdminRole;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.repository.AdminRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminRoleService {

    private final AdminRoleRepository adminRoleRepository;

    public AdminRole add(AdminRole adminRole){
        return adminRoleRepository.save(adminRole);
    }

    public List<Role> getRoles(Long profileId) {
        return adminRoleRepository.findRolesByProfileId(profileId);
    }
}
