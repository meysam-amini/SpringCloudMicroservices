package com.meysam.common.service.impl;

import com.meysam.common.configs.exception.BusinessException;
import com.meysam.common.configs.messages.LocaleMessageSourceService;
import com.meysam.common.dao.MemberRepository;
import com.meysam.common.model.dto.*;
import com.meysam.common.model.entity.Member;
import com.meysam.common.service.api.UserAuthServiceClient;
import com.meysam.common.service.api.MemberService;
import com.meysam.common.service.api.WalletServiceClient;
import com.meysam.common.utils.utils.PredicateUtils;
import com.querydsl.core.types.Predicate;
import jakarta.persistence.EntityManager;
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
    private final UserAuthServiceClient authServiceClient;
    private final EntityManager entityManager;


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
    public Page<MemberDto> pageQuery(MemberFilterDto memberFilterDto) {
        Predicate predicate = PredicateUtils.getPredicate(memberFilterDto.getBooleanExpressions());
        //if different sort was needed (ex: sort by username) :
        /*PageRequest pageRequest = PageRequest.of
                (memberDto.getPageQueryModel().getPageNumber()-1,
                        memberDto.getPageQueryModel().getPageSize(), Sort.Direction.ASC,"username");*/

        /*JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QMember member = QMember.member;
        JPAQuery<Member> query = queryFactory.selectFrom(member)
                .orderBy(new OrderSpecifier(
                        Order.valueOf(Order.ASC.name()),
                        new PathBuilder(Member.class, "id")))
//                .limit(pageRequest.getPageSize())
//                .offset(pageRequest.getPageNumber())
                .where(member.username.like("%" + memberDto.getUsername() + "%"));*/

//        long total = query.fetchCount();
//        List<MemberDto> members= query.fetch().stream().map(MemberDto::maptoMemberDto).toList();

//        return memberRepository.findAll(predicate, memberDto.getPageQueryModel().getPageable());
        return memberRepository.findAll(predicate, memberFilterDto.getPageQueryModel().getPageable()).map(MemberDto::maptoMemberDto);

    }

    @Override
    public Member findById(Long id) {
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
