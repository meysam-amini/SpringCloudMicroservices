package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.dto.PermissionDTO;
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
    Optional<RolePermission> findByPermissionAndRole(Long permission, Long Role);

    List<RolePermission> findByPermission(Long permission);

    Optional<RolePermission> findByRole(Long role);

    @Query("select p.name from Permission p where p.id in(select rp.permission from RolePermission rp where rp.role in(:rolesIds))")
    List<String> findAllPermissionsNamesByRoles(List<Long> rolesIds);

    @Query("select new com.meysam.common.customsecurity.model.dto.PermissionDTO(p.id,p.name,p.code,p.enKey,p.parent) from Permission p where p.id in(select rp.permission from RolePermission rp where rp.role in(:rolesIds)) and p.parent is not null")
    List<PermissionDTO> findAllPermissionsByRoles(List<Long> rolesIds);


    boolean existsByRoleAndPermission(Long role,Long permission);
}