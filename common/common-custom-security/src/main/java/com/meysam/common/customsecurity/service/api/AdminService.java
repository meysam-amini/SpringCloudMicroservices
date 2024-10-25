package com.meysam.common.customsecurity.service.api;

import com.meysam.common.customsecurity.model.dto.ProfileDTO;
import com.meysam.common.customsecurity.model.dto.RegisterUserDto;
import com.meysam.common.customsecurity.model.dto.ResetPasswordDTO;
import com.meysam.common.customsecurity.model.dto.UserInfoDto;
import com.meysam.common.customsecurity.model.entity.Admin;
import com.meysam.common.customsecurity.model.entity.Profile;
import com.meysam.common.model.enums.CaptchaOperation;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    public Profile getAdminByUsername(String username);

    ProfileDTO findById(Long profileId);
    ProfileDTO save(ProfileDTO profileDTO);
    UserInfoDto update(ProfileDTO dto, Long profileId);
    ProfileDTO getProfileByUsername(String username);
    Profile getProfileEntityByUsername(String username);

    ResponseEntity<?> sendOtpForResetPass(String username, String captchaAnswer, CaptchaOperation captchaOperation);
    void resetPassword(String username, ResetPasswordDTO resetPasswordDTO);

    Profile addProfile(RegisterUserDto registerUserDto);

    List<ProfileDTO> findAllByListOfIds(List<Long> listOfIds);

    Page<ProfileDTO> findAll(Predicate predicate, Pageable pageable);
}
