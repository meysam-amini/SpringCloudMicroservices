package com.meysam.common.dao;

import com.meysam.common.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends CrudRepository<User, BigDecimal> {

    User findByEmail(String email);
    User findByUsername(String username);
}
