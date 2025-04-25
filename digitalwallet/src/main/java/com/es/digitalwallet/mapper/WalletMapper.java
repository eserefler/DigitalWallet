package com.es.digitalwallet.mapper;

import com.es.digitalwallet.domain.entity.Wallet;
import com.es.digitalwallet.model.response.GetWalletsResponse;
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
                    return walletDto;
                })
                .toList());
        return getWalletsResponse;
    }
}
