package com.es.digitalwallet.controller;

import com.es.digitalwallet.model.request.ApproveTransactionRequest;
import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.request.DepositToWalletRequest;
import com.es.digitalwallet.model.request.WithdrawFromWalletRequest;
import com.es.digitalwallet.model.response.GetWalletTransactionsResponse;
import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping()
    public ResponseEntity<Void> create(@RequestHeader("x-customer-id") UUID customerIdHeader,
                                       @Valid @RequestBody CreateWalletRequest request) {
        walletService.createWallet(customerIdHeader,request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<GetWalletsResponse> getWallets(@RequestHeader("x-customer-id") UUID customerIdHeader) {
        var result = walletService.getWalletsByCustomerId(customerIdHeader);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{walletId}/deposit")
    public ResponseEntity<Void> deposit(@RequestHeader("x-customer-id") UUID customerIdHeader,
                                        @PathVariable(required = true) UUID walletId,
                                        @Valid @RequestBody DepositToWalletRequest request) {
        walletService.depositToWallet(customerIdHeader,walletId,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{walletId}/withdraw")
    public ResponseEntity<Void> withdraw(@RequestHeader("x-customer-id") UUID customerIdHeader,
                                         @PathVariable UUID walletId,
                                         @Valid @RequestBody WithdrawFromWalletRequest request) {
        walletService.withdrawFromWallet(customerIdHeader,walletId,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<GetWalletTransactionsResponse> getWalletTransactions(@RequestHeader("x-customer-id") UUID customerIdHeader,
                                                                               @PathVariable(required = true) UUID walletId) {
        var result = walletService.getWalletTransactions(customerIdHeader,walletId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{walletId}/transactions/{transactionId}/approve")
    public ResponseEntity<Void> approve(@RequestHeader("x-customer-id") UUID customerIdHeader,
                                        @PathVariable UUID walletId,
                                        @PathVariable UUID transactionId,
                                        @RequestBody ApproveTransactionRequest request) {
        walletService.approveTransaction(customerIdHeader,walletId,transactionId,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
