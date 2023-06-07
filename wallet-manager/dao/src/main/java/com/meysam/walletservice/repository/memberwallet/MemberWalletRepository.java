package com.meysam.walletservice.repository.memberwallet;

import com.meysam.model.entity.MemberWallet;
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

    @Query(value = "select mw.address from MemberWallet mw where mw.memberId=:memberId and mw.coinUnit=:unit")
    String findAddressByMemberIdAndCoinUnit(@Param("memberId") BigDecimal memberId, @Param("unit") String unit);

    List<MemberWallet> findAllWalletsByMemberId(BigDecimal memberId);
}
