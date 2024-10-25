package com.meysam.common.customsecurity.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.customsecurity.model.dto.*;
import com.meysam.common.customsecurity.model.entity.Profile;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.customsecurity.model.enums.UserTypeEnum;
import com.meysam.common.customsecurity.repository.ProfileRepository;
import com.meysam.common.customsecurity.service.api.AdminPermissionService;
import com.meysam.common.customsecurity.service.api.AdminRoleService;
import com.meysam.common.customsecurity.service.api.AdminService;
import com.meysam.common.model.enums.CaptchaOperation;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {


    private final ProfileRepository profileRepository;
    private final RedisTemplate redisTemplate;
    private final AdminRoleService adminRoleService;
    private final RolePermissionServiceImpl rolePermissionServiceImpl;
    private final AdminPermissionService adminPermissionService;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final LocaleMessageSourceService messageSourceService;
    private final CaptchaService captchaService;
    private final ModelMapper modelMapper;

    @Value("${otp.resetpassword.enabled:#{true}}")
    private String OTP_RESET_PASS_ENABLED;


    public Profile getAdminByUsername(String username) {
        return profileRepository.findByUsername(username).orElseThrow(() -> new BusinessException("User not found"));
    }

    @Override
    public ProfileDTO findById(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new BusinessException("profile not found"));
        ProfileDTO profileDTO = modelMapper.map(profile,ProfileDTO.class);
        List<Role> roles = adminRoleService.getRoles(profileId);
        List<Long> rolesIds = roles.stream().map(Role::getId).toList();
        List<String> permissionsByRoles = rolePermissionServiceImpl.getPermissionsNames(rolesIds);
        List<String> directPermissions = adminPermissionService.getAllRolePermissions(profileId)
                .stream().map(PermissionDTO::getName).toList();
        permissionsByRoles.addAll(directPermissions);
        profileDTO.setRoles(roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList()));
        profileDTO.setPermissions(permissionsByRoles);

        return profileDTO;
    }

    @Override
    public ResponseEntity<RestResponseDTO> sendOtpForResetPass(String username, String captchaAnswer, CaptchaOperation captchaOperation) {

        captchaService.validateCaptchaByUsername(captchaAnswer,username, captchaOperation);

        //check for otp configs on profile to see whether otp is set to email or sms
        ProfileDTO profileDTO = getProfileByUsername(username);
        ResponseEntity<RestResponseDTO> response = otpService.sendOtp(null,username, OtpConstants.OtpType.SMS_, OtpConstants.OtpOperation.RESET_PASS_ ,profileDTO.getMobileNumber());
        captchaService.removeCaptchaByUsername(username,captchaOperation);
        return response;
    }

    @Transactional
    @Override
    public void resetPassword(String username, ResetPasswordDTO resetPasswordDTO) {
        Profile profile = getProfileEntityByUsername(username);
        if(Objects.isNull(profile)){
            throw new BusinessException(messageSourceService.getMessage("PROFILE_NOT_FOUND"));
        }
        if(!passwordEncoder.matches(resetPasswordDTO.getOldPassword(),profile.getPassword())){
            throw new BusinessException(messageSourceService.getMessage("OLD_PASSWORD_INCORRECT"));
        }
        if(Boolean.parseBoolean(OTP_RESET_PASS_ENABLED)){
            //check whether otp is set to email or sms
            otpService.validateOtpCode(username, OtpConstants.OtpType.SMS_,OtpConstants.OtpOperation.RESET_PASS_,resetPasswordDTO.getOtpCode());
        }
        profile.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        profileRepository.save(profile);
        if(Boolean.parseBoolean(OTP_RESET_PASS_ENABLED)){
            //check whether otp is set to email or sms
            otpService.removeCachedOtpCodeAndWrongOtpCount(username, OtpConstants.OtpType.SMS_, OtpConstants.OtpOperation.RESET_PASS_);
        }
    }

    @Transactional
    @Override
    public Profile addProfile(RegisterUserDto registerUserDto) {
        if(profileRepository.existsByUsernameOrNationalId(registerUserDto.getUsername(),registerUserDto.getNationalId())){
            throw new BusinessException(messageSourceService.getMessage("USER_ALREADY_EXISTS_BY_NATIONAL_ID_OR_USERNAME"));
        }
        Profile profile = new Profile();
        profile.setUserType(UserTypeEnum.INTERNAL_USER);
        profile.setPassword(passwordEncoder.encode(registerUserDto.getTemporalPassword()));
        profile.setFirstName(registerUserDto.getFirstName());
        profile.setLastName(registerUserDto.getLastName());
        profile.setUsername(registerUserDto.getUsername());
        profile.setAddress(registerUserDto.getAddress());
        profile.setEmail(registerUserDto.getEmail());
        profile.setFatherName(registerUserDto.getFatherName());
        profile.setPhoneNumber(registerUserDto.getPhoneNumber());
        profile.setNationalId(registerUserDto.getNationalId());
        profile.setNationality(registerUserDto.getNationality());
        profile.setIsActive(true);
        profile.setOrganizationName(registerUserDto.getOrganizationName());
        profile.setBirthDate(registerUserDto.getBirthDate());
        profile.setZoneCode(registerUserDto.getZoneCode());
        return profileRepository.save(profile);
    }

    @Override
    public List<ProfileDTO> findAllByListOfIds(List<Long> listOfIds) {
        return mapper.toDtoList(profileRepository.findAllByIdIn(listOfIds));
    }

    @Override
    public Page<ProfileDTO> findAll(Predicate predicate, Pageable pageable) {
        return profileRepository.findAll(predicate,pageable).map(mapper::toDto);
    }

    private ProfileDTO fillInfo(ProfileDTO dto) {
        if (Objects.nonNull(dto.getMaritalStatus()))
            dto.setMaritalStatusDesc(MaritalStatusEnum.getById(dto.getMaritalStatus()).getDesc());

        if (Objects.nonNull(dto.getMilitaryServiceStatus())) {
            GeneralBaseInfoDTO militaryDto = militaryService.findById(dto.getMilitaryServiceStatus());
            if (Objects.nonNull(militaryDto))
                dto.setMilitaryServiceStatusDesc(militaryDto.getName());
        }
        return dto;
    }

    @Override
    @Transactional
    public ProfileDTO save(ProfileDTO profileDTO) {
        Profile entity = mapper.toEntity(profileDTO);
        entity.setPassword(" - ");
        Profile saved = profileRepository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public UserInfoDto update(ProfileDTO dto, Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new BusinessException("Profile not found"));
        profile.setPhysicalCondition(dto.getPhysicalCondition());
        profile.setReligion(dto.getReligion());
        profile.setUserType(dto.getUserType());
        profile.setForeignerId(dto.getForeignerId());
        profile.setMilitaryServiceStatus(dto.getMilitaryServiceStatus());
        profile.setNationality(dto.getNationality());
        profile.setMaritalStatus(dto.getMaritalStatus());
        profile.setChildrenNumber(dto.getChildrenNumber());
        profile.setGraduationLevel(dto.getGraduationLevel());
        profile.setAddress(dto.getAddress());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setMobileNumber(dto.getMobileNumber());
        profile.setFaxNumber(dto.getFaxNumber());
        profile.setEmail(dto.getEmail());
        profile.setPostalCode(dto.getPostalCode());
        profile.setLocation(dto.getLocation());
        profile.setZoneCode(dto.getZoneCode());
        profile.setIsPermittedToReceiveByEmail(dto.getIsPermittedToReceiveByEmail());
        profile.setIsPermittedToReceiveByFax(dto.getIsPermittedToReceiveByFax());
        ProfileResponseDto profileResponseDto = mapper.convertProfileEntityToProfileDto(profileRepository.save(profile), null, null);
        return profileResponseDto.getUserInfo();
    }

    @Override
    public ProfileDTO getProfileByUsername(String username) {
        Profile profile = profileRepository.findByUsername(username).orElseThrow(() -> {
            log.error("User with username :{} not found at time :{}", username, System.currentTimeMillis());
            return new BusinessException("User not found");
        });
        return mapper.toDto(profile);
    }

    @Override
    public Profile getProfileEntityByUsername(String username) {
        return profileRepository.findByUsername(username)
                .orElse(null);
    }
}
