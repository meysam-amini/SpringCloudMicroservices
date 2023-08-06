package com.meysam.common.service.api;

import com.meysam.common.model.dto.*;
import com.meysam.common.model.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.List;

public interface MemberService/* extends UserDetailsService*/ {
  Member createMember(Member user);
  ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto);
  ResponseEntity register(RegisterUserRequestDto registerUserRequestDto);
  Member findByUserName(String username);
  Page<MemberDto> pageQuery(MemberDto memberDto);
  Member findById(BigInteger id);
  List<Member> findAll();
  UserWalletsDto getUserWallets(String token,String username);
  ResponseEntity<String> createMemberWalletAddress(String token,MemberWalletDto memberWalletDto);

//  UserResponseModel getUserByUserID(String id);
}
