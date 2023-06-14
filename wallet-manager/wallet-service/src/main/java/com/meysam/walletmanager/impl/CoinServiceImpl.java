package com.meysam.walletmanager.impl;

import com.meysam.walletmanager.model.entity.Coin;
import com.meysam.walletmanager.dao.repository.coin.CoinRepository;
import com.meysam.walletmanager.api.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService {

    private final CoinRepository coinRepository;

    @Override
    public Coin addCoin(Coin coin) {
        return coinRepository.save(coin);
    }
}
