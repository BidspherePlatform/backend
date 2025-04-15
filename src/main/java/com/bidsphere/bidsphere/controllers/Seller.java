package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.SellerDTO;
import com.bidsphere.bidsphere.entities.Sellers;
import com.bidsphere.bidsphere.repositories.SellersRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/seller")
public class Seller extends SessionizedController {

    private final SellersRepository sellersRepository;

    public Seller(
            SellersRepository sellersRepository
    ) {
        this.sellersRepository = sellersRepository;
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateProfile() {
        // location change

        return ResponseEntity.ok(true);
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller found and returned"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    public ResponseEntity<SellerDTO> getUser(@PathVariable UUID userId) {
        Optional<Sellers> sellerQuery = this.sellersRepository.findById(userId);

        if (sellerQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new SellerDTO(sellerQuery.get()));
    }
}
