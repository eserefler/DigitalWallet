package com.es.digitalwallet.domain.entity;

import com.es.digitalwallet.domain.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import com.es.digitalwallet.domain.enums.Currency;
import java.util.List;

@Entity
@Table(name = "wallet")
@Getter
public class Wallet extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "wallet_name", nullable = false)
    private String walletName;

    @Column(name = "currency", nullable = false)
    private Currency curency;

    @Column(name = "active_for_shopping", nullable = false)
    private Boolean activeForShopping;

    @Column(name = "active_for_withdraw", nullable = false)
    private Boolean activeForWithdraw;

    @Column(name = "balance")
    private long balance;

    @Column(name = "usable_balance")
    private long usableBalance;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    public static Wallet of(Customer customer, String walletName, Currency currency, Boolean activeForShopping, Boolean activeForWithdraw) {
        Wallet wallet = new Wallet();
        wallet.customer = customer;
        wallet.walletName = walletName;
        wallet.curency = currency;
        wallet.activeForShopping = activeForShopping;
        wallet.activeForWithdraw = activeForWithdraw;
        wallet.balance = 0;
        wallet.usableBalance = 0;
        return wallet;
    }

    public void deposit(Transaction newTransaction) {
        if (newTransaction.getStatus() == TransactionStatus.APPROVED) {
            this.balance += newTransaction.getAmount();
            this.usableBalance += newTransaction.getAmount();
        } else if (newTransaction.getStatus() == TransactionStatus.PENDING) {
            this.balance += newTransaction.getAmount();
            ;
        }

        this.getTransactions().add(newTransaction);
    }
}
