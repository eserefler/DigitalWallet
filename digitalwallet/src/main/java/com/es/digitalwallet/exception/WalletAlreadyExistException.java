package com.es.digitalwallet.exception;


import com.es.digitalwallet.domain.enums.ErrorMessages;

public class WalletAlreadyExistException extends RuntimeException{
    public WalletAlreadyExistException(){super(ErrorMessages.WALLET_ALREADY_EXIST.getCODE());}
}
