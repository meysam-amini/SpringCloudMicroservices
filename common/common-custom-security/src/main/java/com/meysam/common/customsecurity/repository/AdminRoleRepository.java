package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.AdminRole;
import com.meysam.common.customsecurity.model.entity.ProfileRole;
import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRoleRepository extends BaseRepository<AdminRole> {

    Optional<AdminRole> findByAdminAndRole(long admin, long role);

    List<AdminRole> findAllByAdmin(long admin);

    @Query(value = "select r from Role r where r.id in (select pr.role from AdminRole pr where pr.admin=:adminId)")
    List<Role> findRolesByProfileId(@Param("adminId") long adminId);

    Optional<AdminRole> findByRole(long role);

    boolean existsByAdminAndRole(long profile, long role);

    void deleteAllByIdIn(List<Long> Ids);

}