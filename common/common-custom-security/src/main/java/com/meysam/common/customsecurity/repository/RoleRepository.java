package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.Role;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {
    Optional<Role> findByCode(String code);

    Optional<Role> findByName(String name);

    boolean existsByNameOrCode(String name,String code);

    Optional<Role> findByCodeAndName(String code, String name);
}