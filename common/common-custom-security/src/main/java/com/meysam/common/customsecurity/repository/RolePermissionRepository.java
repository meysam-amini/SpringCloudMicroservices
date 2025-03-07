package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.dto.PermissionDTO;
import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.customsecurity.model.entity.RolePermission;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query("select new com.meysam.common.customsecurity.model.dto.PermissionDTO(p.id,p.name,p.code,p.enKey,p.parent,p.inMenu) from Permission p where p.id in(select rp.permission from RolePermission rp where rp.role in(:rolesIds))")
    List<PermissionDTO> findAllPermissionsByRoles(List<Long> rolesIds);

    @Query("select p from Permission p where p.id in(select rp.permission from RolePermission rp where rp.role =:roleId)")
    List<Permission> findAllPermissionsByRole(@Param("roleId") long roleId);

    int countAllByRole(long roleId);

    void deleteByRoleAndPermission(long roleId, long permissionId);

    void deleteAllByRoleAndPermissionIsIn(long roleId, List<Long> permissionIds);

    boolean existsByRoleAndPermission(Long role,Long permission);
}