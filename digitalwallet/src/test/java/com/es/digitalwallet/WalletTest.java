package com.es.digitalwallet;

import com.es.digitalwallet.domain.entity.Transaction;
import com.es.digitalwallet.domain.entity.Wallet;
import com.es.digitalwallet.domain.enums.Currency;
import com.es.digitalwallet.domain.enums.OppositePartyType;
import com.es.digitalwallet.exception.InsufficientBalanceException;
import com.es.digitalwallet.exception.WithdrawalAreNotAllowedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WalletTest {

    private Wallet wallet;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        wallet = Wallet.of(UUID.randomUUID(), "Test Wallet", Currency.USD, true, true);
    }

    @Test
    void deposit_withApprovedTransaction_shouldIncreaseBalanceAndUsableBalance() {
        wallet.deposit(100L, OppositePartyType.PAYMENT, "Test Bank");

        assertThat(wallet.getBalance()).isEqualTo(100L);
        assertThat(wallet.getUsableBalance()).isEqualTo(100L);
    }

    @Test
    void deposit_withPendingTransaction_shouldIncreaseBalanceOnly() {
        wallet.deposit(200_000L, OppositePartyType.PAYMENT, "Large Deposit");

        assertThat(wallet.getBalance()).isEqualTo(200_000L);
        assertThat(wallet.getUsableBalance()).isEqualTo(0L);
    }

    @Test
    void withdraw_withSufficientBalance_shouldDecreaseBalanceAndUsableBalance() {
        wallet.deposit(1000L, OppositePartyType.PAYMENT, "Test Bank");
        wallet.withdraw(500L, OppositePartyType.PAYMENT, "Test Shop");

        assertThat(wallet.getBalance()).isLessThanOrEqualTo(1000L);
        assertThat(wallet.getUsableBalance()).isLessThanOrEqualTo(500L);
    }

    @Test
    void withdraw_withInsufficientBalance_shouldThrowException() {
        assertThatThrownBy(() -> wallet.withdraw(100L, OppositePartyType.PAYMENT, "Shop"))
                .isInstanceOf(InsufficientBalanceException.class);
    }

    @Test
    void withdraw_whenWithdrawNotAllowed_shouldThrowException() {
        Wallet noWithdrawWallet = Wallet.of(UUID.randomUUID(), "NoWithdraw Wallet", Currency.USD, true, false);
        noWithdrawWallet.deposit(500L, OppositePartyType.PAYMENT, "Bank");

        assertThatThrownBy(() -> noWithdrawWallet.withdraw(100L, OppositePartyType.PAYMENT, "Shop"))
                .isInstanceOf(WithdrawalAreNotAllowedException.class);
    }

    @Test
    void approveDepositTransaction_shouldMovePendingBalanceToUsableBalance() {
        wallet.deposit(200_000L, OppositePartyType.PAYMENT, "Large Deposit");
        Transaction pendingTransaction = wallet.getTransactions().get(0);

        wallet.approveTransaction(pendingTransaction, true);

        assertThat(wallet.getUsableBalance()).isEqualTo(200_000L);
    }

    @Test
    void rejectDepositTransaction_shouldDecreaseBalance() {
        wallet.deposit(200_000L, OppositePartyType.PAYMENT, "Large Deposit");
        Transaction pendingTransaction = wallet.getTransactions().get(0);

        wallet.approveTransaction(pendingTransaction, false);

        assertThat(wallet.getBalance()).isEqualTo(0L);
    }

    @Test
    void approveWithdrawTransaction_shouldDecreaseBalance() {
        wallet.deposit(500_000L, OppositePartyType.PAYMENT, "Bank");
        Transaction depositTransaction = wallet.getTransactions().get(0);
        wallet.approveTransaction(depositTransaction, true);

        wallet.withdraw(200_000L, OppositePartyType.PAYMENT, "Shop");
        Transaction pendingWithdraw = wallet.getTransactions().get(1);

        wallet.approveTransaction(pendingWithdraw, true);

        assertThat(wallet.getBalance()).isEqualTo(300_000L);
    }


    @Test
    void rejectWithdrawTransaction_shouldIncreaseUsableBalance() {
        wallet.deposit(500_000L, OppositePartyType.PAYMENT, "Bank");
        Transaction pendingDeposit = wallet.getTransactions().get(0);
        wallet.approveTransaction(pendingDeposit, true);

        wallet.withdraw(200_000L, OppositePartyType.PAYMENT, "Shop");
        Transaction pendingWithdraw = wallet.getTransactions().get(1);

        wallet.approveTransaction(pendingWithdraw, false);

        assertThat(wallet.getUsableBalance()).isEqualTo(500_000L);
    }
}
