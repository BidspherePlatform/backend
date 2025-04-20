package com.bidsphere.bidsphere.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class EthereumService {

    private final Web3j web3j;

    public EthereumService(Web3j web3j) {
        this.web3j = web3j;
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

}

