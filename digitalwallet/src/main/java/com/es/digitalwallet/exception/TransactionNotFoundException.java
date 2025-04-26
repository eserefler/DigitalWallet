package com.es.digitalwallet.exception;


import com.es.digitalwallet.domain.enums.ErrorMessages;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(){super(ErrorMessages.TRANSACTION_NOT_FOUND.getCODE());}
}
