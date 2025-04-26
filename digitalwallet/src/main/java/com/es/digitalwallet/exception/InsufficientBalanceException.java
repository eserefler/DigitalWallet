package com.es.digitalwallet.exception;


import com.es.digitalwallet.domain.enums.ErrorMessages;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(){super(ErrorMessages.INSUFFICIENT_BALANCE.getCODE());}
}
