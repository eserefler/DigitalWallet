package org.es.auth.exception;


import org.es.auth.domain.enums.ErrorMessages;

public class PhoneAlreadyExistException extends RuntimeException {
    public PhoneAlreadyExistException() {
        super(ErrorMessages.PHONE_ALREADY_EXIST.getCODE());
    }
}
