package com.es.digitalwallet.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    CUSTOMER_NOT_FOUND("Customer not found.","ERR100"),
    WALLET_ALREADY_EXIST("Wallet already exist.","ERR101"),
    WALLET_NOT_FOUND("Wallet not found.","ERR102"),
    TRANSACTION_NOT_FOUND("Transaction not found.","ERR103"),
    TRANSACTION_CANT_BE_APPROVED_OR_DENIED("Transaction can't be approved or denied.","ERR104"),
    INSUFFICIENT_BALANCE("Insufficient balance.","ERR105"),
    WITHDRAWALS_ARE_NOT_ALLOWED("Withdrawals are not allowed.","ERR106"),;

    private final String MESSAGE;
    private final String CODE;
}
