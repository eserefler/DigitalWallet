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

public class UserController {
    private final WalletService walletService;
    public UserController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("users/{userId}/wallets")
    public ResponseEntity<GetWalletsResponse> getWalletsByUserId(@PathVariable(required = true) UUID userId) {
        var result = walletService.getWalletsByUserId(userId);
        return new ResponseEntity<GetWalletsResponse>(result, HttpStatus.OK);
    }
}
