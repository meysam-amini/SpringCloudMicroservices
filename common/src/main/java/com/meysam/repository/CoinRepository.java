package com.meysam.repository;

import com.meysam.model.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface CoinRepository extends JpaRepository<Coin, BigDecimal> {
}
