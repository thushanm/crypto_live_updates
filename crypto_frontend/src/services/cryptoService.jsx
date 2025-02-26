export const fetchCryptoData = async () => {
    try {
        const response = await fetch("http://localhost:8080/crypto/prices");
        if (!response.ok) {
            throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        return data.map((crypto) => ({
            symbol: crypto.symbol,
            name: crypto.symbol.replace("USDT", ""), // Extract name from symbol
            current_price: parseFloat(crypto.lastPrice), // Last price
            price_change_percentage_24h: parseFloat(crypto.priceChangePercent), // 24h change
            market_cap: crypto.quoteVolume, // Use volume as a proxy for market cap (Binance does not provide exact market cap)
            image: `https://cryptoicons.org/api/icon/${crypto.symbol.toLowerCase()}/50`, // Example URL for icons
        }));
    } catch (error) {
        console.error("Error fetching crypto data:", error);
        return [];
    }
};
