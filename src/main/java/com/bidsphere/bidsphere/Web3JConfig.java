package com.bidsphere.bidsphere;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3JConfig {

    private static final String INFURA_URL = "https://sepolia.infura.io/v3/883a451a8e5e4f1593dcdc4ab913acdf";

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(INFURA_URL));
    }
}

