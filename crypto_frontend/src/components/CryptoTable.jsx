import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography} from "@mui/material";

const CryptoTable = ({ data }) => {
    const cryptoData = Array.isArray(data) ? data : [];

    if (cryptoData.length === 0) {
        return <Typography variant="body1">No cryptocurrency data available.</Typography>;
    }

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Logo</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Price (USD)</TableCell>
                        <TableCell>24h Change</TableCell>
                        <TableCell>Market Cap</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {cryptoData.map((crypto) => (
                        <TableRow key={`${crypto.id || crypto.symbol}`}>
                            <TableCell>
                                <img src={crypto.image} alt={crypto.name} width="30" height="30" />
                            </TableCell>
                            <TableCell>{crypto.name}</TableCell>
                            <TableCell>
                                ${crypto.current_price ? crypto.current_price.toFixed(2) : "N/A"}
                            </TableCell>
                            <TableCell
                                style={{
                                    color: crypto.price_change_percentage_24h > 0 ? "green" : "red",
                                }}
                            >
                                {crypto.price_change_percentage_24h
                                    ? crypto.price_change_percentage_24h.toFixed(2)
                                    : "N/A"}
                                %
                            </TableCell>
                            <TableCell>
                                {crypto.market_cap
                                    ? `$${crypto.market_cap.toLocaleString()}`
                                    : "N/A"}
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default CryptoTable;
