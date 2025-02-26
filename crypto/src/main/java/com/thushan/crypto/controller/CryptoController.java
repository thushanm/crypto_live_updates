package com.thushan.crypto.controller;

import com.thushan.crypto.service.BinanceWebSocketService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class CryptoController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.binance.com/api/v3/ticker/24hr";

    // WebSocket service to listen to real-time updates
    private final BinanceWebSocketService binanceWebSocketService;

    public CryptoController(BinanceWebSocketService binanceWebSocketService) {
        this.binanceWebSocketService = binanceWebSocketService;
    }

    // Get latest prices using REST API (called every second for real-time updates)
    @GetMapping("/crypto/prices")
    public List<?> getPrices() {
        // Fetch all trading pairs from Binance API
        Object[] response = restTemplate.getForObject(apiUrl, Object[].class);
        if (response == null) return List.of();

        // Select the first 20 coins for display
        return Arrays.stream(response).limit(20).collect(Collectors.toList());
    }

    // This endpoint will allow clients to connect and receive real-time data from WebSocket
    // This is just an example, real-time data can be fetched directly via WebSocket.
    @GetMapping("/crypto/subscribe")
    public String subscribeToWebSocket() {
        // Subscribe to Binance WebSocket to get real-time updates
        // WebSocket is handled in the background by the BinanceWebSocketService
        return "WebSocket subscription is now active for real-time updates.";
    }
}
