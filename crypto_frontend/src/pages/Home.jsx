import React, { useEffect, useState } from "react";
import { Container, Typography, CircularProgress, Alert } from "@mui/material";
import CryptoTable from "../components/CryptoTable";
import { fetchCryptoData } from "../services/cryptoService";

const Home = () => {
    const [cryptoData, setCryptoData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getData = async () => {
            try {
                const data = await fetchCryptoData();
                setCryptoData(data);
            } catch (err) {
                setError("Failed to fetch crypto data.");
            } finally {
                setLoading(false);
            }
        };

        getData();
    }, []);

    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Crypto Market Prices
            </Typography>
            {loading ? (
                <CircularProgress />
            ) : error ? (
                <Alert severity="error">{error}</Alert>
            ) : (
                <CryptoTable data={cryptoData} />
            )}
        </Container>
    );
};

export default Home;
