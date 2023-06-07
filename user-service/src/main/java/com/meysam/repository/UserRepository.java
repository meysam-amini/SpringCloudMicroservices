package com.meysam.repository;

import com.meysam.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);
    User findByUserId(String id);
}
