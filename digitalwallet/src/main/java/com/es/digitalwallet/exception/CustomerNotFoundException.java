package com.es.digitalwallet.exception;


import com.es.digitalwallet.domain.enums.ErrorMessages;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(){super(ErrorMessages.CUSTOMER_NOT_FOUND.getCODE());}
}
