package com.es.digitalwallet.model.response.dto;

import com.es.digitalwallet.domain.enums.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletDto {
    private String walletName;
    private String curency;
    private Boolean activeForShopping;
    private Boolean activeForWithdraw;
}
