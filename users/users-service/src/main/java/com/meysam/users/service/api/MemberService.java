package com.meysam.users.service.api;

import com.meysam.common.model.dto.*;
import com.meysam.common.model.entity.Member;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface MemberService/* extends UserDetailsService*/ {
  Member createMember(Member user);
  ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto);
  ResponseEntity register(RegisterUserRequestDto registerUserRequestDto);
  Member findByUserName(String username);
  Member findById(BigDecimal id);
  List<Member> findAll();
  UserWalletsDto getUserWallets(String token,String username);
  ResponseEntity<String> createMemberWalletAddress(String token,MemberWalletDto memberWalletDto);

//  UserResponseModel getUserByUserID(String id);
}
