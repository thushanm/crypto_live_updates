package com.thushan.crypto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CryptoController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1";
    @GetMapping("/crypto/prices")
    public String getPrices() {
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
