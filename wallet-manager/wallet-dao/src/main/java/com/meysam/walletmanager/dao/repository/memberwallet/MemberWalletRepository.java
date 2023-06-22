package com.meysam.walletmanager.dao.repository.memberwallet;

import com.meysam.common.model.dto.MemberWalletDto;
import com.meysam.common.model.entity.MemberWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MemberWalletRepository extends JpaRepository<MemberWallet, BigDecimal> {

    @Query(value = "select mw.address from MemberWallet mw where mw.address =: address")
    String returnAddressIfExists(@Param("address") String address);

    @Query(value = "select mw.address from MemberWallet mw where mw.member.Id=:memberId and mw.coinUnit=:unit")
    String findAddressByMemberAndCoinUnit(@Param("memberId") BigDecimal memberId, @Param("unit") String unit);

    @Query(value = "select new com.meysam.common.model.dto.MemberWalletDto(mw.member.Id,mw.coinUnit,mw.address) from MemberWallet mw where mw.member.Id=:memberId")
    List<MemberWalletDto> findAllWalletsByMember(BigDecimal memberId);
}
