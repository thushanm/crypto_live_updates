const API_URL = "http://localhost:8080/crypto/prices";
const WS_URL = "ws://localhost:8080/ws/crypto";

export const fetchCryptoData = async () => {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Failed to fetch data");
        return await response.json();
    } catch (error) {
        console.error("Error fetching crypto data:", error);
        return [];
    }
};

export const connectWebSocket = (updateData) => {
    let ws;

    const connect = () => {
        ws = new WebSocket(WS_URL);

        ws.onopen = () => console.log("WebSocket connected.");
        ws.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);
                updateData(data);
            } catch (error) {
                console.error("Error parsing WebSocket message:", error);
            }
        };

        ws.onerror = (error) => console.error("WebSocket Error:", error);
        ws.onclose = () => {
            console.log("WebSocket disconnected. Reconnecting in 2 seconds...");
            setTimeout(connect, 2000); // Auto-reconnect
        };
    };

    connect();
    return () => ws?.close();
};
