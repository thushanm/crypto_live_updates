package com.thushan.crypto.service;

import com.thushan.crypto.handler.CryptoWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CryptoPriceService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.binance.com/api/v3/ticker/24hr";
    private String cachedData = "{}"; // Cache for storing the latest data

    @Scheduled(fixedRate = 1000) // Fetch data every second
    public void fetchCryptoPrices() {
        try {
            String prices = restTemplate.getForObject(apiUrl, String.class);
            cachedData = prices; // Update cached data
            CryptoWebSocketHandler.broadcast(prices); // Broadcast to WebSocket clients
        } catch (Exception e) {
            System.err.println("Error fetching prices: " + e.getMessage());
            // Fallback: Send cached data to clients if there's an error
            CryptoWebSocketHandler.broadcast(cachedData);
        }
    }
}
