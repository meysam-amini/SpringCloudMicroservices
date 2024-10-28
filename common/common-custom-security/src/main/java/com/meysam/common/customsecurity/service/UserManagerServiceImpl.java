package com.meysam.common.customsecurity.service;


import com.meysam.common.customsecurity.model.dto.ProfileDTO;
import com.meysam.common.customsecurity.model.dto.ProfileQueryModel;
import com.meysam.common.customsecurity.model.dto.RegisterUserDto;
import com.meysam.common.customsecurity.model.entity.Profile;
import com.meysam.common.customsecurity.service.api.ProfilePermissionService;
import com.meysam.common.customsecurity.service.api.ProfileRoleService;
import com.meysam.common.customsecurity.service.api.ProfileService;
import com.meysam.common.model.pagination.PageQueryModel;
import com.meysam.common.utils.utils.PredicateUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl implements UserManagerService {

    private final ProfileService profileService;
    private final ProfileRoleService profileRoleService;
    private final ProfilePermissionService profilePermissionService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ProfileDTO addUser(RegisterUserDto registerUserDto) {
        Profile newProfile = profileService.addProfile(registerUserDto);

        if(Objects.nonNull(registerUserDto.getPermissions())&&!registerUserDto.getPermissions().isEmpty()) {
            profilePermissionService.assignPermissionsToProfile(registerUserDto.getPermissions(), registerUserDto.getUsername());
        }
        if(Objects.nonNull(registerUserDto.getRoles())&&!registerUserDto.getRoles().isEmpty()) {
            profileRoleService.assignRolesToProfile(registerUserDto.getRoles(), registerUserDto.getUsername());
        }
        return modelMapper.map(newProfile,ProfileDTO.class);
    }

    @Override
    public Page<ProfileDTO> queryUsers(ProfileQueryModel profileQueryModel) {
        Predicate predicate = PredicateUtils.getPredicate(profileQueryModel.getBooleanExpressions());
        PageQueryModel pageModel = new PageQueryModel();
        pageModel.setPageNumber(profileQueryModel.getPageNumber());
        pageModel.setPageSize(profileQueryModel.getPageSize());
        if (Objects.isNull(profileQueryModel.getSort())) {
            pageModel.setDefaultSort();
        } else {
            pageModel.setSort(profileQueryModel.getSort());
        }
        if (Objects.isNull(profileQueryModel.getDirection())) {
            pageModel.setDirection(Arrays.asList(Sort.Direction.DESC));
        } else {
            pageModel.setDirection(Collections.singletonList(profileQueryModel.getDirection()));
        }
        Pageable pageable = pageModel.getPageable();

        return profileService.findAll(predicate, pageable);
    }
}
