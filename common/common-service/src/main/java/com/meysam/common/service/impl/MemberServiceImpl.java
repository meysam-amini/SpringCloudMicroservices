package com.meysam.common.service.impl;

import com.meysam.common.dao.MemberRepository;
import com.meysam.common.model.dto.*;
import com.meysam.common.model.entity.Member;
import com.meysam.common.service.api.AuthServiceClient;
import com.meysam.common.service.api.MemberService;
import com.meysam.common.service.api.WalletServiceClient;
import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.common.utils.utils.PredicateUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
    public Page<MemberDto> pageQuery(MemberDto memberDto) {
        Predicate predicate = PredicateUtils.getPredicate(memberDto.getBooleanExpressions());
        //if different sort was needed (ex: sort by username) :
        /*PageRequest pageRequest = PageRequest.of
                (memberDto.getPageQueryModel().getPageNumber(),
                        memberDto.getPageQueryModel().getPageSize(), Sort.Direction.ASC,"username");*/
        Page<Member> stakingPlanDetailPage = memberRepository.findAll(predicate, memberDto.getPageQueryModel().getPageable());
        return stakingPlanDetailPage.map(MemberDto::maptoMemberDto);
    }

    @Override
    public Member findById(BigInteger id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public List<Member> findAll() {
        return (List<Member>) memberRepository.findAll();
    }

    @Override
    public UserWalletsDto getUserWallets(String token, String username) {

        Member user = memberRepository.findByUsername(username);
        if(user!=null){

            List<MemberWalletDto> wallets = walletServiceClient.getWallets(token, user.getUsername());

            MemberDto userDto = MemberDto.builder()
                    .email(user.getEmail())
                    .username(username)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();

            return UserWalletsDto.builder().wallets(wallets).user(userDto).build();
        }
        else {
            throw new BusinessException(HttpStatus.NOT_FOUND,messageSourceService.getMessage("USER_NOT_FOUND"));
        }
    }

    @Override
    public ResponseEntity<String> createMemberWalletAddress(String token, MemberWalletDto memberWalletDto) {
        return walletServiceClient.createWallet(token,memberWalletDto);
    }
}
