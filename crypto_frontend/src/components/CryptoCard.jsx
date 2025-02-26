import React from "react";
import { Card, CardContent, Typography } from "@mui/material";

const CryptoCard = ({ crypto }) => (
    <Card sx={{ marginBottom: 2 }}>
        <CardContent>
            <Typography variant="h6">{crypto.symbol}</Typography>
            <Typography variant="body1">Price: {parseFloat(crypto.lastPrice).toFixed(8)}</Typography>
            <Typography variant="body2" style={{ color: parseFloat(crypto.priceChangePercent) > 0 ? "green" : "red" }}>
                24h Change: {parseFloat(crypto.priceChangePercent).toFixed(2)}%
            </Typography>
        </CardContent>
    </Card>
);

export default CryptoCard;
