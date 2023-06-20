package com.meysam.users.service.api;

import com.meysam.common.model.dto.LoginRequestDto;
import com.meysam.common.model.dto.RegisterUserRequestDto;
import com.meysam.common.model.dto.UserWalletsDto;
import com.meysam.common.model.entity.Member;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface MemberService/* extends UserDetailsService*/ {
  Member createMember(Member user);
  ResponseEntity login(LoginRequestDto loginRequestDto);
  ResponseEntity register(RegisterUserRequestDto registerUserRequestDto);
  Member findByUserName(String username);
  Member findById(BigDecimal id);
  List<Member> findAll();
  UserWalletsDto getUserWallets(String username);
//  UserResponseModel getUserByUserID(String id);
}
