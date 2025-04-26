package com.es.digitalwallet.exception;


import com.es.digitalwallet.domain.enums.ErrorMessages;

public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException(){super(ErrorMessages.WALLET_NOT_FOUND.getCODE());}
}
