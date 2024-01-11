package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.AdminPermission;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminPermissionRepository extends BaseRepository<AdminPermission> {

    Optional<AdminPermission> findByPermission(BigInteger permission);

    List<AdminPermission> findAllByAdmin(BigInteger admin);

    @Query(value = "select p.name from Permission p where p.id in (select ap.permission from AdminPermission ap where ap.admin =:adminId)")
    List<String> findPermissionsNamesByAdmin(@Param("adminId") BigInteger adminId);

    Optional<AdminPermission> findByAdminAndPermission(BigInteger admin, BigInteger permission);

}