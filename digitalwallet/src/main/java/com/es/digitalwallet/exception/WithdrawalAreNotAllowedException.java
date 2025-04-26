package com.es.digitalwallet.exception;


import com.es.digitalwallet.domain.enums.ErrorMessages;

public class WithdrawalAreNotAllowedException extends RuntimeException{
    public WithdrawalAreNotAllowedException(){super(ErrorMessages.WALLET_NOT_FOUND.getCODE());}
}
