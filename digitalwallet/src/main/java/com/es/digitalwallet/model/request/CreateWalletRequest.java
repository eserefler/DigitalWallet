package com.es.digitalwallet.model.request;

import com.es.digitalwallet.domain.enums.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletRequest {
    private String name;
    private Currency currency;
    private Boolean activeForShopping;
    private Boolean activeForWithdraw;
}
