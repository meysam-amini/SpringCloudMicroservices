package com.meysam.common.customsecurity.service;

import com.meysam.common.customsecurity.model.dto.ProfileDTO;
import com.meysam.common.customsecurity.model.dto.ProfileQueryModel;
import com.meysam.common.customsecurity.model.dto.RegisterUserDto;
import org.springframework.data.domain.Page;

public interface UserManagerService {


    ProfileDTO addUser(RegisterUserDto registerUserDto);

    Page<ProfileDTO> queryUsers(ProfileQueryModel profileQueryModel);
}
