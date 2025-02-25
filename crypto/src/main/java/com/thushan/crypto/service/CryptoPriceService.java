package com.thushan.crypto.service;

import com.thushan.crypto.handler.CryptoWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CryptoPriceService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=usd";
    @Scheduled(fixedRate = 10000)
    public void fetchCryptoPrice(){
        String price = restTemplate.getForObject(apiUrl, String.class);
        CryptoWebSocketHandler.broadcast(price);

    }
}
