package com.es.digitalwallet.exception;


import com.es.digitalwallet.domain.enums.ErrorMessages;

public class TransactionCantBeApprovedOrDeniedException extends RuntimeException{
    public TransactionCantBeApprovedOrDeniedException(){super(ErrorMessages.TRANSACTION_CANT_BE_APPROVED_OR_DENIED.getCODE());}
}
