package com.meysam.service.coinService;

import com.meysam.model.entity.Coin;
import com.meysam.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService{

    private CoinRepository coinRepository;

    @Override
    public Coin addCoin(Coin coin) {
        return coinRepository.save(coin);
    }
}
