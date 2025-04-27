package com.es.digitalwallet.service;

import com.es.digitalwallet.model.request.ApproveTransactionRequest;
import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.request.DepositToWalletRequest;
import com.es.digitalwallet.model.request.WithdrawFromWalletRequest;
import com.es.digitalwallet.model.response.GetWalletTransactionsResponse;
import com.es.digitalwallet.model.response.GetWalletsResponse;

import java.util.UUID;

public interface WalletService {
     void createWallet(UUID customerId,CreateWalletRequest request);

    GetWalletsResponse getWalletsByCustomerId(UUID customerId);

    void depositToWallet(UUID customerId,UUID walletId, DepositToWalletRequest request);

    GetWalletTransactionsResponse getWalletTransactions(UUID customerId,UUID walletId);

    void withdrawFromWallet(UUID customerId,UUID walletId, WithdrawFromWalletRequest request);

    void approveTransaction(UUID customerId,UUID walletId, UUID transactionId, ApproveTransactionRequest request);
}