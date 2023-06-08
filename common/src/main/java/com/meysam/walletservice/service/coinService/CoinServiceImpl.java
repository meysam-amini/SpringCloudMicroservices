package com.meysam.walletservice.service.coinService;

import com.meysam.repository.CoinRepository;
import com.meysam.walletmanager.model.entity.Coin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService{

    private final CoinRepository coinRepository;

    @Override
    public Coin addCoin(Coin coin) {
        return coinRepository.save(coin);
    }
}
