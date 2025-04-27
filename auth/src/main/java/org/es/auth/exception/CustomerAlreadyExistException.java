package org.es.auth.exception;

import org.es.auth.domain.enums.ErrorMessages;

public class CustomerAlreadyExistException extends RuntimeException {
    public CustomerAlreadyExistException() {
        super(ErrorMessages.CUSTOMER_ALREADY_EXIST.getCODE());
    }
}
