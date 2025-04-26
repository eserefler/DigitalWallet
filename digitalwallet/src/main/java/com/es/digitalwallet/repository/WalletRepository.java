package com.es.digitalwallet.repository;

import com.es.digitalwallet.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByCustomerIdAndWalletName(UUID customerId, String walletName);

    List<Wallet> findAllByCustomerId(UUID userId);

   Wallet findById(UUID walletId);
}

