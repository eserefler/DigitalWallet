package com.es.digitalwallet.controller;

import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class WalletController {

    private final WalletService walletService;
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("info")
    public ResponseEntity<String> getInfo(){

        return new ResponseEntity<>("Service is running :)", HttpStatus.OK);
    }

    @PostMapping("wallets")
    public ResponseEntity<Void> create(@RequestHeader String customerId, @RequestBody CreateWalletRequest request) {
        walletService.createWallet(UUID.fromString(customerId),request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("users/{userId}/wallets")
    public ResponseEntity<GetWalletsResponse> getWalletsByUserId(@PathVariable(required = true) UUID userId) {
        var result = walletService.getWalletsByUserId(userId);
        return new ResponseEntity<GetWalletsResponse>(result, HttpStatus.OK);
    }
}
