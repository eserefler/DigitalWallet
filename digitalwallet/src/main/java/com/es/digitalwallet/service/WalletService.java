package com.es.digitalwallet.service;

import com.es.digitalwallet.domain.entity.Wallet;
import com.es.digitalwallet.mapper.WalletMapper;
import com.es.digitalwallet.model.request.ApproveTransactionRequest;
import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.request.DepositToWalletRequest;
import com.es.digitalwallet.model.request.WithdrawRequest;
import com.es.digitalwallet.model.response.GetWalletTransactionsResponse;
import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.repository.CustomerRepository;
import com.es.digitalwallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface WalletService {
     void createWallet(UUID customerId,CreateWalletRequest request);

    GetWalletsResponse getWalletsByUserId(UUID userId);

    void depositToWallet(UUID walletId, DepositToWalletRequest request);

    GetWalletTransactionsResponse getWalletTransactions(UUID walletId);

    void withdrawFromWallet(UUID walletId, WithdrawRequest request);

    void approveTransaction(UUID walletId, UUID transactionId, ApproveTransactionRequest request);

    @Service
    class WalletServiceImpl implements WalletService {
        private final WalletRepository walletRepository;
        private final CustomerRepository customerRepository;

        public WalletServiceImpl(WalletRepository walletRepository, CustomerRepository customerRepository) {
            this.walletRepository = walletRepository;
            this.customerRepository = customerRepository;
        }

        public void createWallet(UUID customerId, CreateWalletRequest request) {
            var customer = customerRepository.findById(customerId);
            if (customer == null)
                throw new RuntimeException("customer not found");

            var wallet = walletRepository.findByCustomerIdAndWalletName(customerId, request.getName());
            if (wallet != null) {
                throw new IllegalArgumentException("Wallet with this name already exists");
            }

            var newWallet = Wallet.of(customer, request.getName(), request.getCurrency(), request.getActiveForShopping(), request.getActiveForWithdraw());
            walletRepository.save(newWallet);
        }

        public GetWalletsResponse getWalletsByUserId(UUID userId) {
           var wallets = walletRepository.findAllByCustomerId(userId);
           return WalletMapper.toGetWalletsResponse(wallets);
        }


        public void depositToWallet(UUID walletId, DepositToWalletRequest request) {
            var wallet = walletRepository.findById(walletId);
            if (wallet == null) {
                throw new IllegalArgumentException("Wallet not found");
            }
            wallet.deposit(request.getAmount(), request.getOppositeParty(), request.getSource());
            walletRepository.save(wallet);
        }

        public void withdrawFromWallet(UUID walletId, WithdrawRequest request) {
            var wallet = walletRepository.findById(walletId);
            if (wallet == null) {
                throw new IllegalArgumentException("Wallet not found");
            }
            wallet.withdraw(request.getAmount(), request.getOppositeParty(), request.getDestination());
            walletRepository.save(wallet);
        }

        public void approveTransaction(UUID walletId, UUID transactionId, ApproveTransactionRequest request) {
            var wallet = walletRepository.findById(walletId);
            if (wallet == null) {
                throw new IllegalArgumentException("Wallet not found");
            }

            var transaction = wallet.getTransactions().stream()
                    .filter(t -> t.getId().equals(transactionId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

            wallet.approveTransaction(transaction, request.getIsApproved());
            walletRepository.save(wallet);
        }

        public GetWalletTransactionsResponse getWalletTransactions(UUID walletId) {
            var wallet = walletRepository.findById(walletId);
            return WalletMapper.toGetWalletTransactionsResponse(wallet.getTransactions(),wallet.getCurency().toString());
        }
    }
}