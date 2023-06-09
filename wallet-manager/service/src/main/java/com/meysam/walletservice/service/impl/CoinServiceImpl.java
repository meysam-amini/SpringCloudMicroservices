package com.meysam.walletservice.service.impl;

import com.meysam.walletmanager.model.entity.Coin;
import com.meysam.walletservice.repository.coin.CoinRepository;
import com.meysam.walletservice.service.api.CoinService;
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
