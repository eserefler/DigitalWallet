package com.es.digitalwallet.domain.entity;

import jakarta.persistence.Column;
import lombok.Getter;

import java.util.Currency;
import java.util.UUID;

@Getter
public class Wallet  extends BaseEntity{

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "wallet_name", nullable = false)
    private UUID walletName;

    @Column(name = "currency", nullable = false)
    private Currency curency;

    @Column(name = "active_for_shopping", nullable = false)
    private Boolean activeForShopping;

    @Column(name = "active_for_withdraw", nullable = false)
    private Boolean activeForWithdraw;

    public static Wallet of(UUID customerId, UUID walletName, Currency currency, Boolean activeForShopping, Boolean activeForWithdraw) {
        Wallet wallet = new Wallet();
        wallet.customerId = customerId;
        wallet.walletName = walletName;
        wallet.curency = currency;
        wallet.activeForShopping = activeForShopping;
        wallet.activeForWithdraw = activeForWithdraw;
        return wallet;
    }
}
