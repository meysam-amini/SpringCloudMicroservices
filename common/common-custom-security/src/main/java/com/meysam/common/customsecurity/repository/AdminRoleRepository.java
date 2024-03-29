package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.AdminRole;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRoleRepository extends BaseRepository<AdminRole> {

    Optional<AdminRole> findByAdminAndRole(Long admin, Long role);

    List<AdminRole> findAllByAdmin(Long admin);

    @Query(value = "select r from Role r where r.id in (select pr.role from AdminRole pr where pr.admin=:adminId)")
    List<Role> findRolesByProfileId(@Param("adminId") Long adminId);

    Optional<AdminRole> findByRole(Long role);

}