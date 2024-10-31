package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.ProfileRole;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRoleRepository extends BaseRepository<ProfileRole> {

        Optional<ProfileRole> findByProfileAndRole(long profile, long role);

        List<ProfileRole> findAllByProfile(long profile);

        @Query(value = "select r from Role r where r.id in (select pr.role from ProfileRole pr where pr.profile=:profileId)")
        List<Role> findRolesByProfileId(@Param("profileId") long profileId);

        Optional<ProfileRole> findByRole(long role);

        boolean existsByProfileAndRole(long profile, long role);

        void deleteAllByIdIn(List<Long> Ids);

    }

