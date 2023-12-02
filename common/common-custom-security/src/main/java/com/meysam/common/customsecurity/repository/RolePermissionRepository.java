package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.RolePermission;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionRepository extends BaseRepository<RolePermission> {
    Optional<RolePermission> findByPermissionAndRole(BigInteger permission, BigInteger Role);

    List<RolePermission> findByPermission(BigInteger permission);

    Optional<RolePermission> findByRole(BigInteger role);

    @Query("select p.name from Permission p where p.id in(select rp.permission from RolePermission rp where rp.role in(:rolesIds))")
    List<String> findAllPermissionsNamesByRoles(List<BigInteger> rolesIds);
}