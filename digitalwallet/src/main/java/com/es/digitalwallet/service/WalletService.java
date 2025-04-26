package com.es.digitalwallet.service;

import com.es.digitalwallet.domain.entity.Transaction;
import com.es.digitalwallet.domain.entity.Wallet;
import com.es.digitalwallet.domain.enums.OppositePartyType;
import com.es.digitalwallet.domain.enums.TransactionType;
import com.es.digitalwallet.mapper.WalletMapper;
import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.request.DepositToWalletRequest;
import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.repository.CustomerRepository;
import com.es.digitalwallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.UUID;

public interface WalletService {
     void createWallet(UUID customerId,CreateWalletRequest request);

    GetWalletsResponse getWalletsByUserId(UUID userId);

    void depositToWallet(UUID walletId, DepositToWalletRequest request);

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

            var newTransaction = Transaction.of(wallet, request.getAmount(), TransactionType.DEPOSIT, request.getOppositeParty(), request.getSource());
            wallet.deposit(newTransaction);

            walletRepository.save(wallet);
        }
    }
}
// todo:
// hata mesajları class olacak
// hata filter eklenecek
// enumlar string olarak db'de tutulacak
// "activeForShopping": true,
//            "activeForWithdraw": true,  bu alanları işlemlerde kontrol et.