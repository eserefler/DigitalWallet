package com.es.digitalwallet.domain.entity;

import com.es.digitalwallet.domain.enums.OppositePartyType;
import com.es.digitalwallet.domain.enums.TransactionType;
import com.es.digitalwallet.exception.InsufficientBalanceException;
import com.es.digitalwallet.exception.WithdrawalAreNotAllowedException;
import jakarta.persistence.*;
import lombok.Getter;
import com.es.digitalwallet.domain.enums.Currency;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "wallet")
@Getter
public class Wallet extends BaseEntity {

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

    @Column(name = "balance")
    private long balance;

    @Column(name = "usable_balance")
    private long usableBalance;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    public static Wallet of(UUID customerId, String walletName, Currency currency, Boolean activeForShopping, Boolean activeForWithdraw) {
        Wallet wallet = new Wallet();
        wallet.customerId = customerId;
        wallet.walletName = walletName;
        wallet.curency = currency;
        wallet.activeForShopping = activeForShopping;
        wallet.activeForWithdraw = activeForWithdraw;
        wallet.balance = 0;
        wallet.usableBalance = 0;
        return wallet;
    }

    public void deposit(long amount, OppositePartyType oppositePartyType, String oppositeParty) {
        var depositTransaction = Transaction.of(this,amount, TransactionType.DEPOSIT,oppositePartyType,oppositeParty);
        this.transactions.add(depositTransaction);

        if (depositTransaction.isApproved()) {
            this.increaseUsableBalance(depositTransaction.getAmount());
            this.increaseBalance(depositTransaction.getAmount());
        } else if (depositTransaction.isPending()) {
            this.increaseBalance(depositTransaction.getAmount());
        }
    }

    public void withdraw(long amount, OppositePartyType oppositePartyType, String oppositeParty) {
        if (amount > this.usableBalance)
            throw new InsufficientBalanceException();

        if(!canWithdraw())
            throw new WithdrawalAreNotAllowedException();

        var withdrawTransaction = Transaction.of(this,amount, TransactionType.WITHDRAW,oppositePartyType,oppositeParty);
        this.transactions.add(withdrawTransaction);

        if (withdrawTransaction.isApproved()) {
            this.decreaseBalance(withdrawTransaction.getAmount());
            this.decreaseUsableBalance(withdrawTransaction.getAmount());
        } else if (withdrawTransaction.isPending()) {
            this.decreaseUsableBalance(withdrawTransaction.getAmount());
        }
    }

    public void approveTransaction(Transaction transaction, Boolean isApproved) {
        transaction.setStatus(isApproved);

        if (isApproved) {
            if (transaction.isDepositTransaction()) {
                increaseUsableBalance(transaction.getAmount());
            } else if (transaction.isWithdrawTransaction()) {
                decreaseBalance(transaction.getAmount());
            }
        } else {
            if (transaction.isDepositTransaction()) {
                decreaseBalance(transaction.getAmount());
            } else if (transaction.isWithdrawTransaction()) {
                increaseUsableBalance(transaction.getAmount());
            }
        }
    }

    private Boolean canWithdraw() {
        return activeForWithdraw && activeForShopping;
    }

    private void increaseUsableBalance(long amount) {
        this.usableBalance += amount;
    }
    private void decreaseUsableBalance(long amount) {
        this.usableBalance -= amount;
    }
    private void increaseBalance(long amount) {
        this.balance += amount;
    }
    private void decreaseBalance(long amount) {
        this.balance -= amount;
    }
}
