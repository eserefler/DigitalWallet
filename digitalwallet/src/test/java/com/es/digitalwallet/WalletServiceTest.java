package com.es.digitalwallet;

import com.es.digitalwallet.domain.entity.Wallet;
import com.es.digitalwallet.domain.entity.Transaction;
import com.es.digitalwallet.domain.enums.Currency;
import com.es.digitalwallet.exception.TransactionNotFoundException;
import com.es.digitalwallet.exception.WalletAlreadyExistException;
import com.es.digitalwallet.exception.WalletNotFoundException;
import com.es.digitalwallet.mapper.WalletMapper;
import com.es.digitalwallet.model.request.ApproveTransactionRequest;
import com.es.digitalwallet.model.request.CreateWalletRequest;
import com.es.digitalwallet.model.request.DepositToWalletRequest;
import com.es.digitalwallet.model.request.WithdrawFromWalletRequest;
import com.es.digitalwallet.model.response.GetWalletTransactionsResponse;
import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.repository.WalletRepository;
import com.es.digitalwallet.service.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    private UUID customerId;
    private UUID walletId;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerId = UUID.randomUUID();
        walletId = UUID.randomUUID();
        transactionId = UUID.randomUUID();
    }

    @Test
    void createWallet_shouldSaveNewWallet() {
        CreateWalletRequest request = new CreateWalletRequest();
        request.setName("Test Wallet");
        request.setCurrency(Currency.USD);
        request.setActiveForShopping(true);
        request.setActiveForWithdraw(true);

        when(walletRepository.findByCustomerIdAndWalletName(customerId, request.getName())).thenReturn(null);

        walletService.createWallet(customerId, request);

        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    void createWallet_shouldThrowWalletAlreadyExistException() {
        CreateWalletRequest request = new CreateWalletRequest();
        request.setName("Test Wallet");

        when(walletRepository.findByCustomerIdAndWalletName(customerId, request.getName())).thenReturn(new Wallet());

        assertThatThrownBy(() -> walletService.createWallet(customerId, request))
                .isInstanceOf(WalletAlreadyExistException.class);
    }

    @Test
    void getWalletsByCustomerId_shouldReturnWallets() {
        Wallet wallet = Wallet.of(customerId, "Test Wallet", Currency.USD, true, true);

        when(walletRepository.findAllByCustomerId(customerId)).thenReturn(List.of(wallet));

        GetWalletsResponse response = walletService.getWalletsByCustomerId(customerId);

        verify(walletRepository).findAllByCustomerId(customerId);
        assert response != null;
    }

    @Test
    void depositToWallet_shouldDepositAndSave() {
        DepositToWalletRequest request = new DepositToWalletRequest();
        request.setAmount(100L);
        request.setSource("Bank");

        Wallet wallet = mock(Wallet.class);

        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(wallet);

        walletService.depositToWallet(customerId, walletId, request);

        verify(wallet).deposit(eq(100L), any(), eq("Bank"));
        verify(walletRepository).save(wallet);
    }

    @Test
    void depositToWallet_shouldThrowWalletNotFoundException() {
        DepositToWalletRequest request = new DepositToWalletRequest();

        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(null);

        assertThatThrownBy(() -> walletService.depositToWallet(customerId, walletId, request))
                .isInstanceOf(WalletNotFoundException.class);
    }

    @Test
    void withdrawFromWallet_shouldWithdrawAndSave() {
        WithdrawFromWalletRequest request = new WithdrawFromWalletRequest();
        request.setAmount(50L);
        request.setDestination("ATM");

        Wallet wallet = mock(Wallet.class);

        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(wallet);

        walletService.withdrawFromWallet(customerId, walletId, request);

        verify(wallet).withdraw(eq(50L), any(), eq("ATM"));
        verify(walletRepository).save(wallet);
    }

    @Test
    void withdrawFromWallet_shouldThrowWalletNotFoundException() {
        WithdrawFromWalletRequest request = new WithdrawFromWalletRequest();

        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(null);

        assertThatThrownBy(() -> walletService.withdrawFromWallet(customerId, walletId, request))
                .isInstanceOf(WalletNotFoundException.class);
    }

    @Test
    void approveTransaction_shouldApproveAndSave() {
        ApproveTransactionRequest request = new ApproveTransactionRequest();
        request.setApproved(true);

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);

        Wallet wallet = mock(Wallet.class);
        when(wallet.getTransactions()).thenReturn(List.of(transaction));
        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(wallet);

        walletService.approveTransaction(customerId, walletId, transactionId, request);

        verify(wallet).approveTransaction(eq(transaction), eq(true));
        verify(walletRepository).save(wallet);
    }

    @Test
    void approveTransaction_shouldThrowWalletNotFoundException() {
        ApproveTransactionRequest request = new ApproveTransactionRequest();

        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(null);

        assertThatThrownBy(() -> walletService.approveTransaction(customerId, walletId, transactionId, request))
                .isInstanceOf(WalletNotFoundException.class);
    }

    @Test
    void approveTransaction_shouldThrowTransactionNotFoundException() {
        ApproveTransactionRequest request = new ApproveTransactionRequest();

        Wallet wallet = mock(Wallet.class);
        when(wallet.getTransactions()).thenReturn(List.of());
        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(wallet);

        assertThatThrownBy(() -> walletService.approveTransaction(customerId, walletId, transactionId, request))
                .isInstanceOf(TransactionNotFoundException.class);
    }

    @Test
    void getWalletTransactions_shouldReturnTransactions() {
        Wallet wallet = mock(Wallet.class);
        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(wallet);
        when(wallet.getTransactions()).thenReturn(List.of());
        when(wallet.getCurency()).thenReturn(Currency.USD);

        GetWalletTransactionsResponse response = walletService.getWalletTransactions(customerId, walletId);

        verify(walletRepository).findByIdAndCustomerId(walletId, customerId);
        assert response != null;
    }

    @Test
    void getWalletTransactions_shouldThrowWalletNotFoundException() {
        when(walletRepository.findByIdAndCustomerId(walletId, customerId)).thenReturn(null);

        assertThatThrownBy(() -> walletService.getWalletTransactions(customerId, walletId))
                .isInstanceOf(WalletNotFoundException.class);
    }
}

