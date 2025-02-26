package com.thushan.crypto.service;

import com.thushan.crypto.handler.CryptoWebSocketHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import org.springframework.stereotype.Service;

import java.net.URI;

@ClientEndpoint
@Service
public class BinanceWebSocketService {
    private static final String BINANCE_WS_URL = "wss://stream.binance.com:9443/ws/!ticker@arr";
    private Session session;

    @PostConstruct
    public void connect() {
        try {
            WebSocketContainer container = jakarta.websocket.ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, URI.create(BINANCE_WS_URL));
            System.out.println("Connected to Binance WebSocket.");
        } catch (Exception e) {
            System.err.println("Error connecting to Binance WebSocket: " + e.getMessage());
        }
    }

    @OnMessage
    public void onMessage(String message) {
        // Broadcast received message to WebSocket clients
        CryptoWebSocketHandler.broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }

    @PreDestroy
    public void disconnect() {
        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing Binance WebSocket connection: " + e.getMessage());
        }
    }
}
