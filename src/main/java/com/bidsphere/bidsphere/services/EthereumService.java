package com.bidsphere.bidsphere.services;

import com.bidsphere.contracts.BidsphereBiddingEscrow;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.UUID;


@Service
public class EthereumService {

    private static final Dotenv dotenv = Dotenv.load();

    private final Web3j web3j;

    private final String contractAddress = dotenv.get("CONTRACT_ADDRESS");
    private final Credentials credentials = Credentials.create(dotenv.get("HOLDER_PRIVATE_KEY"));
    private final String gasPrice = dotenv.get("GAS_PRICE") != null ? dotenv.get( "GAS_PRICE") : "10";
    private final ContractGasProvider gasProvider = new StaticGasProvider(
            Convert.toWei(this.gasPrice, Convert.Unit.GWEI).toBigInteger(),
            BigInteger.valueOf(50_000)
    );

    public final BidsphereBiddingEscrow contract;

    public EthereumService(Web3j web3j) {
        this.web3j = web3j;
        this.contract = BidsphereBiddingEscrow.load(contractAddress, web3j, credentials, gasProvider);
    }

    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public String getClientVersion() throws Exception {
        Web3ClientVersion version = web3j.web3ClientVersion().send();
        return version.getWeb3ClientVersion();
    }

    public double fetchEthUsdPrice() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=usd"))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        return root.path("ethereum").path("usd").doubleValue();
    }

    public double getEthereumBalance(String walletAddress) throws Exception {
        BigInteger sepoliaBalance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST)
                .send()
                .getBalance();

        return Convert.fromWei(String.valueOf(sepoliaBalance), Convert.Unit.ETHER).doubleValue();
    }

    public double getUSDBalance(String walletAddress) throws Exception {
        double ethereumAmount = this.getEthereumBalance(walletAddress);
        double conversionRate = this.fetchEthUsdPrice();

        return conversionRate * ethereumAmount;
    }

    public Optional<Transaction> getTransactionByHash(String transactionHash) throws Exception {
        EthTransaction transactionResponse = web3j.ethGetTransactionByHash(transactionHash).send();

        return transactionResponse.getTransaction();
    }

    public Boolean matchesContractAddress(String contractAddress) {
        return contractAddress.equals(this.contractAddress);
    }

}

