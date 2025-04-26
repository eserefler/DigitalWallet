package com.es.digitalwallet.controller;

import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController

public class CustomerController {
    private final WalletService walletService;
    public CustomerController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("customers/{customerId}/wallets")
    public ResponseEntity<GetWalletsResponse> getWalletsByUserId(@PathVariable(required = true) UUID customerId) {
        var result = walletService.getWalletsByCustomerId(customerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
