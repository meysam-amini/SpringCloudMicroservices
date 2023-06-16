package com.meysam.common.dao;

import com.meysam.common.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);
    User findByUsername(String username);
}
