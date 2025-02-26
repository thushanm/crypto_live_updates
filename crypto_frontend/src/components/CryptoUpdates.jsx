import React, { useEffect, useState } from "react";
import CryptoTable from "../components/CryptoTable";
import CryptoCard from "../components/CryptoCard";
import { fetchCryptoData } from "../services/cryptoService";

const CryptoUpdates = () => {
    const [cryptoData, setCryptoData] = useState([]);

    useEffect(() => {
        const ws = new WebSocket("ws://localhost:8080/ws/crypto");

        ws.onopen = () => {
            console.log("WebSocket connection opened");
        };

        ws.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);
                setCryptoData(data);
            } catch (error) {
                console.error("Error parsing WebSocket message:", error);
            }
        };

        ws.onerror = (error) => {
            console.error("WebSocket Error: ", error);
        };

        ws.onclose = () => {
            console.log("WebSocket closed.");
        };

        return () => {
            if (ws.readyState === WebSocket.OPEN || ws.readyState === WebSocket.CONNECTING) {
                ws.close();
            }
        };
    }, []);

    useEffect(() => {
        fetchCryptoData().then(setCryptoData);
    }, []);

    return (
        <div>
            <h1>Crypto Updates</h1>
            <CryptoTable data={cryptoData} />
            {cryptoData.map((crypto) => (
                <CryptoCard key={crypto.symbol} crypto={crypto} />
            ))}
        </div>
    );
};

export default CryptoUpdates;
