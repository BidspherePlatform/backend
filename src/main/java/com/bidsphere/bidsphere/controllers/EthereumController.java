package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.services.EthereumService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eth")
public class EthereumController {

    private static final Dotenv dotenv = Dotenv.load();

    private final EthereumService service;
    private final String walletAddress = dotenv.get("WALLET_ADDRESS");

    public EthereumController(EthereumService service) {
        this.service = service;
    }

    @GetMapping("/version")
    public String getVersion() throws Exception {
        return service.getClientVersion();
    }

    @GetMapping("/balance")
    public String getBalance() throws Exception {
        return "Amount: " + this.service.getUSDBalance(this.walletAddress);
    }
}

