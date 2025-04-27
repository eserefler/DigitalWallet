package com.es.digitalwallet.model.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WalletDto {
    private UUID id;
    private String walletName;
    private String curency;
    private boolean activeForShopping;
    private boolean activeForWithdraw;
    private long balance;
    private long usableBalance;
}
