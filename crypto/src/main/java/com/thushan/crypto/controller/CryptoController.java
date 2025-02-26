package com.thushan.crypto.controller;

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

    @GetMapping("/crypto/prices")
    public List<?> getPrices() {
        // Fetch all trading pairs
        Object[] response = restTemplate.getForObject(apiUrl, Object[].class);
        if (response == null) return List.of();

        // Select the first 20 coins
        return Arrays.stream(response).limit(20).collect(Collectors.toList());
    }
}
