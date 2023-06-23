package com.meysam.users.service.impl;

import com.meysam.common.dao.MemberRepository;
import com.meysam.common.model.dto.*;
import com.meysam.common.model.entity.Member;
import com.meysam.common.model.entity.MemberWallet;
import com.meysam.common.service.api.AuthServiceClient;
import com.meysam.users.service.api.MemberService;
import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.WalletServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LocaleMessageSourceService messageSourceService;
    private final WalletServiceClient walletServiceClient;
    private final AuthServiceClient authServiceClient;


    @Override
    public Member createMember(Member user) {
        return memberRepository.save(user);
    }

    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        return authServiceClient.userLogin(loginRequestDto);
    }

    @Override
    public ResponseEntity register(RegisterUserRequestDto registerUserRequestDto) {
        return authServiceClient.registerUser(registerUserRequestDto);
    }

    @Override
    public Member findByUserName(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public Member findById(BigDecimal id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public List<Member> findAll() {
        return (List<Member>) memberRepository.findAll();
    }

    @Override
    public UserWalletsDto getUserWallets(String username) {

        Member user = memberRepository.findByUsername(username);
        if(user!=null){

            List<MemberWalletDto> wallets = walletServiceClient.getWallets(user.getUsername()).getBody();

            UserDto userDto = UserDto.builder().id(user.getId()).username(username).build();

            return UserWalletsDto.builder().wallets(wallets).userDto(userDto).build();
        }
        else {
            throw new BusinessException(HttpStatus.NOT_FOUND,messageSourceService.getMessage("USER_NOT_FOUND"));
        }
    }

    @Override
    public ResponseEntity<String> createMemberWalletAddress(MemberWalletDto memberWalletDto) {
        return walletServiceClient.createWallet(memberWalletDto);
    }
}
