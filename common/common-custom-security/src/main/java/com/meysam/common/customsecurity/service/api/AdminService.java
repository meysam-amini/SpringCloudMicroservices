package com.meysam.common.customsecurity.service.api;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.customsecurity.model.dto.ProfileDTO;
import com.meysam.common.customsecurity.model.dto.ResetPasswordDTO;
import com.meysam.common.customsecurity.model.dto.UserInfoDto;
import com.meysam.common.customsecurity.model.entity.Admin;
import com.meysam.common.customsecurity.model.entity.Profile;
import com.meysam.common.customsecurity.repository.AdminRepository;
import com.meysam.common.model.enums.CaptchaOperation;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {

    public Admin add(Admin admin);

    public Admin getAdminByUsername(String username);

    ProfileDTO findById(Long profileId);
    ProfileDTO save(ProfileDTO profileDTO);
    UserInfoDto update(ProfileDTO dto, Long profileId);
    ProfileDTO getProfileByUsername(String username);
    Profile getProfileEntityByUsername(String username);
    boolean hasPermission(String permission, Long profileId);
    ProfileDTO getProfileIdAndNameAndLastnameByNationalCode(String nationalId);
    ProfileDTO getProfileByNationalId(String nationalId);

    ProfileDTO findByIdFullInfo(Long profileId);
    ResponseEntity<?> sendOtpForResetPass(String username, String captchaAnswer, CaptchaOperation captchaOperation);
    void resetPassword(String username, ResetPasswordDTO resetPasswordDTO);

    Profile addProfile(RegisterUserDto registerUserDto);

    List<ProfileDTO> findAllByListOfIds(List<Long> listOfIds);

    Page<ProfileDTO> findAll(Predicate predicate, Pageable pageable);
}
