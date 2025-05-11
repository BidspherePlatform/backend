package com.bidsphere.bidsphere;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;


@Configuration
public class Web3JConfig {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String INFURA_URL = "https://sepolia.infura.io/v3/" + dotenv.get("INFURA_KEY");

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(INFURA_URL));
    }
}

