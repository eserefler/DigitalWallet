package com.es.digitalwallet.controller;

import com.es.digitalwallet.model.request.ApproveTransactionRequest;
import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.request.DepositToWalletRequest;
import com.es.digitalwallet.model.request.WithdrawRequest;
import com.es.digitalwallet.model.response.GetWalletTransactionsResponse;
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
    public ResponseEntity<Void> create(@RequestHeader UUID customerId, @RequestBody CreateWalletRequest request) {
        walletService.createWallet(customerId,request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("wallets/{walletId}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable(required = true) UUID walletId, @RequestBody DepositToWalletRequest request) {
        walletService.depositToWallet(walletId,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("wallets/{walletId}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable UUID walletId, @RequestBody WithdrawRequest request) {
        walletService.withdrawFromWallet(walletId,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("wallets/{walletId}/transactions")
    public ResponseEntity<GetWalletTransactionsResponse> getWalletTransactions(@PathVariable(required = true) UUID walletId) {
        var result = walletService.getWalletTransactions(walletId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("wallets/{walletId}/transactions/{transactionId}/approve")
    public ResponseEntity<Void> approve(@PathVariable UUID walletId, @PathVariable UUID transactionId,@RequestBody ApproveTransactionRequest request) {
        walletService.approveTransaction(walletId,transactionId,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
