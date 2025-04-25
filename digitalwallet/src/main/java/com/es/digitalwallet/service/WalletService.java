package com.es.digitalwallet.service;

import com.es.digitalwallet.domain.entity.Wallet;
import com.es.digitalwallet.mapper.WalletMapper;
import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.UUID;

public interface WalletService {
     void createWallet(UUID customerId,CreateWalletRequest request);

    GetWalletsResponse getWalletsByUserId(UUID userId);

    @Service
    class WalletServiceImpl implements WalletService {
        private final WalletRepository walletRepository;

        public WalletServiceImpl(WalletRepository walletRepository) {
            this.walletRepository = walletRepository;
        }

        public void createWallet(UUID customerId, CreateWalletRequest request) {
            var wallet = walletRepository.findByCustomerIdAndWalletName(customerId, request.getName());
            if (wallet != null) {
                throw new IllegalArgumentException("Wallet with this name already exists");
            }

            var newWallet = Wallet.of(customerId, request.getName(), request.getCurrency(), request.getActiveForShopping(), request.getActiveForWithdraw());
            walletRepository.save(newWallet);
        }

        public GetWalletsResponse getWalletsByUserId(UUID userId) {
           var wallets = walletRepository.findAllByCustomerId(userId);
           return WalletMapper.toGetWalletsResponse(wallets);
        }
    }
}
