package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.Admin;
import com.meysam.common.dao.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends BaseRepository<Admin> {

    List<Admin> findByFirstName(String firstName);

    List<Admin> findByLastName(String lastName);

    Optional<Admin> findByNationalId(String nationalId);

    Optional<Admin> findByUsernameAndPasswordAndIsActive(String username, String pass, Boolean active);

    Optional<Admin> findByNationalIdAndPasswordAndIsActive(String nationalId, String pass, Boolean active);

    Optional<Admin> findByUsername(String username);
}
