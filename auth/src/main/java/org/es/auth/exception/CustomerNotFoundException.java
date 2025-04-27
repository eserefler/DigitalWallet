package org.es.auth.exception;

import org.es.auth.domain.enums.ErrorMessages;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException() {
        super(ErrorMessages.CUSTOMER_NOT_FOUND.getCODE());
    }
}
