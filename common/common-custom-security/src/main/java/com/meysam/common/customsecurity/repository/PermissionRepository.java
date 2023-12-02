package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.Permission;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PermissionRepository extends BaseRepository<Permission> {

    Optional<Permission> findByNameAndCode(String name, String code);

    Optional<Permission> findByName(String name);

    Optional<Permission> findByCode(String code);
}