package com.meysam.walletmanager.dao.repository.coin;

import com.meysam.common.model.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CoinRepository extends JpaRepository<Coin, BigDecimal> {
}
