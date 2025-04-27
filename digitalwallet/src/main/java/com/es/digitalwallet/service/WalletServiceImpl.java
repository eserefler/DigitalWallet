package com.es.digitalwallet.service;

import com.es.digitalwallet.domain.entity.Wallet;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public void createWallet(UUID customerId , CreateWalletRequest request) {
        var wallet = walletRepository.findByCustomerIdAndWalletName(customerId, request.getName());
        if (wallet != null)
            throw new WalletAlreadyExistException();

        var newWallet = Wallet.of(customerId, request.getName(), request.getCurrency(), request.getActiveForShopping(), request.getActiveForWithdraw());
        walletRepository.save(newWallet);
    }

    public GetWalletsResponse getWalletsByCustomerId(UUID customerId) {
        var wallets = walletRepository.findAllByCustomerId(customerId);
        return WalletMapper.toGetWalletsResponse(wallets);
    }

    public void depositToWallet(UUID customerId,UUID walletId, DepositToWalletRequest request) {
        var wallet = walletRepository.findByIdAndCustomerId(walletId,customerId);
        if (wallet == null) {
            throw new WalletNotFoundException();
        }
        wallet.deposit(request.getAmount(), request.getOppositeParty(), request.getSource());
        walletRepository.save(wallet);
    }

    public void withdrawFromWallet(UUID customerId,UUID walletId, WithdrawFromWalletRequest request) {
        var wallet = walletRepository.findByIdAndCustomerId(walletId,customerId);
        if (wallet == null)
            throw new WalletNotFoundException();
        wallet.withdraw(request.getAmount(), request.getOppositeParty(), request.getDestination());
        walletRepository.save(wallet);
    }

    public void approveTransaction(UUID customerId,UUID walletId, UUID transactionId, ApproveTransactionRequest request) {
        var wallet = walletRepository.findByIdAndCustomerId(walletId,customerId);
        if (wallet == null)
            throw new WalletNotFoundException();

        var transaction = wallet.getTransactions().stream()
                .filter(t -> t.getId().equals(transactionId))
                .findFirst()
                .orElseThrow(TransactionNotFoundException::new);

        wallet.approveTransaction(transaction, request.getIsApproved());
        walletRepository.save(wallet);
    }

    public GetWalletTransactionsResponse getWalletTransactions(UUID customerId, UUID walletId) {
        var wallet = walletRepository.findByIdAndCustomerId(walletId,customerId);
        if (wallet == null)
            throw new WalletNotFoundException();
        return WalletMapper.toGetWalletTransactionsResponse(wallet.getTransactions(),wallet.getCurency().toString());
    }
}