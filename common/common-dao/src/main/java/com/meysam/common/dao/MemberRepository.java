package com.meysam.common.dao;

import com.meysam.common.model.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MemberRepository extends CrudRepository<Member, BigDecimal> {

    Member findByEmail(String email);
    Member findByUsername(String username);
}
