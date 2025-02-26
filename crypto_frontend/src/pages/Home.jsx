import React, { useEffect, useState } from "react";
import { fetchCryptoData, connectWebSocket } from "../services/cryptoService";
import CryptoTable from "../components/CryptoTable";
import CryptoCard from "../components/CryptoCard";
import { Typography, Box } from "@mui/material";

const Home = () => {
    const [cryptoData, setCryptoData] = useState([]);

    useEffect(() => {
        fetchCryptoData().then(setCryptoData);
        const cleanup = connectWebSocket(setCryptoData);
        return cleanup;
    }, []);

    return (
        <Box p={3}>
            <Typography variant="h4" gutterBottom>Crypto Updates</Typography>
            <CryptoTable data={cryptoData} />
            {cryptoData.map(crypto => <CryptoCard key={crypto.symbol} crypto={crypto} />)}
        </Box>
    );
};

export default Home;
