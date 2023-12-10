package com.meysam.common.customsecurity.service;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.entity.Admin;
import com.meysam.common.customsecurity.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;


    public Admin add(Admin admin){
        return adminRepository.save(admin);
    }

    public Admin getProfileByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(() -> new BusinessException("User not found"));
    }
}
