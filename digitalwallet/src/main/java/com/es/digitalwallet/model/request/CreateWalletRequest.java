package com.es.digitalwallet.model.request;

import com.es.digitalwallet.domain.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateWalletRequest {
    @NotNull
    private String name;
    private Currency currency;
    private Boolean activeForShopping;
    private Boolean activeForWithdraw;
}
