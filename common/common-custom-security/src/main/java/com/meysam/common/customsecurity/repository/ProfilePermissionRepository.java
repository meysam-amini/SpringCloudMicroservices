package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.entity.ProfilePermission;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfilePermissionRepository extends BaseRepository<ProfilePermission> {

    Optional<ProfilePermission> findByPermission(long permission);

    List<ProfilePermission> findAllByProfile(long profile);

    @Query(value = "select new com.meysam.common.customsecurity.model.dto.PermissionDTO(p.id,p.name,p.code,p.enKey,p.parent) from Permission p where p.id in (select pp.permission from ProfilePermission pp where pp.profile =:profileId) and p.parent is not null")
    List<PermissionDTO> findAllPermissionsByProfile(@Param("profileId") long profileId);

    boolean existsByPermissionAndProfile(long permission,long profile);

    void deleteAllByIdIn(List<Long> Ids);
}