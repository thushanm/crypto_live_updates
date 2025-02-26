import React from "react";
import { Card, CardContent, Typography } from "@mui/material";

const CryptoCard = ({ name, price, change }) => {
    return (
        <Card sx={{ marginBottom: 2 }}>
            <CardContent>
                <Typography variant="h6">{name}</Typography>
                <Typography variant="body1">Price: ${price.toFixed(2)}</Typography>
                <Typography variant="body2" style={{ color: change > 0 ? "green" : "red" }}>
                    24h Change: {change.toFixed(2)}%
                </Typography>
            </CardContent>
        </Card>
    );
};

export default CryptoCard;
