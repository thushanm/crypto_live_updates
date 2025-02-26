package com.thushan.crypto.service;

import com.thushan.crypto.handler.CryptoWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class CryptoPriceService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1";
    private String cachedData = "{}"; // Store last successful response
    private long retryDelay = 10000; // Initial delay 10s

    @Scheduled(fixedRate = 30000) // Fetch every 30 seconds
    public void fetchCryptoPrices() {
        try {
            String prices = restTemplate.getForObject(apiUrl, String.class);
            cachedData = prices; // Update cache
            retryDelay = 10000; // Reset retry delay after success
            CryptoWebSocketHandler.broadcast(prices);
        } catch (HttpClientErrorException.TooManyRequests e) {
            System.out.println("Rate limit exceeded. Retrying after " + (retryDelay / 1000) + " seconds...");
            try {
                TimeUnit.MILLISECONDS.sleep(retryDelay);
                retryDelay = Math.min(retryDelay * 2, 60000); // Exponential backoff (max 60s)
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            CryptoWebSocketHandler.broadcast(cachedData); // Send cached data to clients
        }
    }
}
