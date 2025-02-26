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
    private final String apiUrl = "https://api.binance.com/api/v3/ticker/24hr";
    private String cachedData = "{}"; // Cache to store last successful response
    private long retryDelay = 1000; // Retry delay in milliseconds (initial: 1 second)

    @Scheduled(fixedRate = 1000) // Run every second
    public void fetchCryptoPrices() {
        try {
            // Fetch data for all coins
            String prices = restTemplate.getForObject(apiUrl, String.class);
            cachedData = prices; // Update the cache with the latest data
            retryDelay = 1000; // Reset retry delay after success

            // Broadcast updated prices to WebSocket clients
            CryptoWebSocketHandler.broadcast(prices);
        } catch (HttpClientErrorException.TooManyRequests e) {
            // Handle rate limiting (HTTP 429)
            System.out.println("Rate limit exceeded. Retrying after " + (retryDelay / 1000) + " seconds...");
            try {
                TimeUnit.MILLISECONDS.sleep(retryDelay);
                retryDelay = Math.min(retryDelay * 2, 60000); // Exponential backoff (max 60 seconds)
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            // Send cached data to clients to avoid disruption
            CryptoWebSocketHandler.broadcast(cachedData);
        } catch (Exception e) {
            // Handle other exceptions (e.g., network errors)
            System.err.println("Error fetching crypto prices: " + e.getMessage());
            // Send cached data to clients to ensure continuity
            CryptoWebSocketHandler.broadcast(cachedData);
        }
    }
}
