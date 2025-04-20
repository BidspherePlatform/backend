package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.services.EthereumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eth")
public class EthereumController {

    private final EthereumService service;

    public EthereumController(EthereumService service) {
        this.service = service;
    }

    @GetMapping("/version")
    public String getVersion() throws Exception {
        return service.getClientVersion();
    }

    @GetMapping("/balance")
    public String getBalance() throws Exception {
        String walletAddress = "0x50bBfb70866F4453E296bAa904636236D7728359";

        return "Amount: " + this.service.getUSDBalance(walletAddress);
    }
}

