package com.meysam.common.dao;

import com.meysam.common.model.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends BaseRepository<Member> {

    Member findByEmail(String email);
    Member findByUsername(String username);
}
