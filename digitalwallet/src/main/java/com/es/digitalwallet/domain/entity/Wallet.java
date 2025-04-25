package com.es.digitalwallet.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import com.es.digitalwallet.domain.enums.Currency;

import java.util.UUID;

@Entity
@Table(name = "wallet")
@Getter
public class Wallet  extends BaseEntity{

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "wallet_name", nullable = false)
    private String walletName;

    @Column(name = "currency", nullable = false)
    private Currency curency;

    @Column(name = "active_for_shopping", nullable = false)
    private Boolean activeForShopping;

    @Column(name = "active_for_withdraw", nullable = false)
    private Boolean activeForWithdraw;

    /*
     @Column(name = "balance", nullable = false)
     private long balance;

     @Column(name = "usable_balance", nullable = false)
     private long usableBalance;
     */

    public static Wallet of(UUID customerId, String walletName, Currency currency, Boolean activeForShopping, Boolean activeForWithdraw) {
        Wallet wallet = new Wallet();
        wallet.customerId = customerId;
        wallet.walletName = walletName;
        wallet.curency = currency;
        wallet.activeForShopping = activeForShopping;
        wallet.activeForWithdraw = activeForWithdraw;
        return wallet;
    }
}
