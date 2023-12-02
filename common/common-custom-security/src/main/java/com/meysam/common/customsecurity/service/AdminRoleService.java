package com.meysam.common.customsecurity.service;


import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.repository.AdminRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminRoleService {

    private final AdminRoleRepository profileRoleRepository;

    public List<Role> getRoles(BigInteger profileId) {
        return profileRoleRepository.findRolesByProfileId(profileId);
    }
}
