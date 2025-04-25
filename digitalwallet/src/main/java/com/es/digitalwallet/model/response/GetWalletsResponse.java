package com.es.digitalwallet.model.response;

import com.es.digitalwallet.model.response.dto.WalletDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetWalletsResponse {
    private List<WalletDto> wallets;
}