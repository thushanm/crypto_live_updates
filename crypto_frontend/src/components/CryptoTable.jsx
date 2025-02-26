import React from "react";
import { TableContainer, Table, TableHead, TableRow, TableCell, TableBody, Paper, Typography } from "@mui/material";

const CryptoTable = ({ data }) => {
    if (data.length === 0) {
        return <Typography variant="body1">No cryptocurrency data available.</Typography>;
    }

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Symbol</TableCell>
                        <TableCell>Last Price</TableCell>
                        <TableCell>24h Change (%)</TableCell>
                        <TableCell>24h High</TableCell>
                        <TableCell>24h Low</TableCell>
                        <TableCell>Volume</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.map((crypto) => (
                        <TableRow key={crypto.symbol}>
                            <TableCell>{crypto.symbol}</TableCell>
                            <TableCell>{parseFloat(crypto.lastPrice).toFixed(8)}</TableCell>
                            <TableCell style={{ color: parseFloat(crypto.priceChangePercent) > 0 ? "green" : "red" }}>
                                {parseFloat(crypto.priceChangePercent).toFixed(2)}%
                            </TableCell>
                            <TableCell>{parseFloat(crypto.highPrice).toFixed(8)}</TableCell>
                            <TableCell>{parseFloat(crypto.lowPrice).toFixed(8)}</TableCell>
                            <TableCell>{parseFloat(crypto.volume).toFixed(2)}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default CryptoTable;
