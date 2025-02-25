package com.thushan.crypto.service;

import com.thushan.crypto.handler.CryptoWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CryptoPriceService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1";
    @Scheduled(fixedRate = 60000) // Fetch every 60 seconds
    public void fetchCryptoPrices() {
        try {
            String prices = restTemplate.getForObject(apiUrl, String.class);
            CryptoWebSocketHandler.broadcast(prices);
        } catch (HttpClientErrorException.TooManyRequests e) {
            System.out.println("Rate limit exceeded. Retrying after some time...");
            try {
                Thread.sleep(60000); // Wait 60 seconds before retrying
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
