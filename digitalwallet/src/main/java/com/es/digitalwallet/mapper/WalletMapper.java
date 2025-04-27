package com.es.digitalwallet.mapper;

import com.es.digitalwallet.domain.entity.Transaction;
import com.es.digitalwallet.domain.entity.Wallet;
import com.es.digitalwallet.model.response.GetWalletTransactionsResponse;
import com.es.digitalwallet.model.response.GetWalletsResponse;
import com.es.digitalwallet.model.response.dto.TransactionDto;
import com.es.digitalwallet.model.response.dto.WalletDto;

import java.util.List;

public class WalletMapper {
    public static GetWalletsResponse toGetWalletsResponse(List<Wallet> wallets) {
        GetWalletsResponse getWalletsResponse = new GetWalletsResponse();
        getWalletsResponse.setWallets(wallets.stream()
                .map(wallet -> {
                    var walletDto = new WalletDto();
                    walletDto.setWalletName(wallet.getWalletName());
                    walletDto.setCurency(wallet.getCurency().toString());
                    walletDto.setActiveForShopping(wallet.getActiveForShopping());
                    walletDto.setActiveForWithdraw(wallet.getActiveForWithdraw());
                    walletDto.setBalance(wallet.getBalance());
                    walletDto.setUsableBalance(wallet.getUsableBalance());
                    return walletDto;
                })
                .toList());

        return getWalletsResponse;
    }

    public static GetWalletTransactionsResponse toGetWalletTransactionsResponse(List<Transaction> transactions,String currency) {
        var response = new GetWalletTransactionsResponse();
        response.setTransactions(transactions.stream()
                .map(transaction -> {
                    var transactionDto = new TransactionDto();
                    transactionDto.setId(transaction.getId());
                    transactionDto.setOppositeParty(transaction.getOppositeParty());
                    transactionDto.setOppositePartyType(transaction.getOppositePartyType().toString());
                    transactionDto.setType(transaction.getType().toString());
                    transactionDto.setAmount(transaction.getAmount());
                    transactionDto.setCurrency(currency);
                    transactionDto.setCreatedAt(transaction.getCreatedAt());
                    transactionDto.setUpdatedAt(transaction.getUpdatedAt());
                    transactionDto.setStatus(transaction.getStatus().toString());
                    return transactionDto;
                })
                .toList());

        return response;
    }
}
