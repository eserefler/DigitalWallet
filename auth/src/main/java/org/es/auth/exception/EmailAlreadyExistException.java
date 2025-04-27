package org.es.auth.exception;


import org.es.auth.domain.enums.ErrorMessages;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super(ErrorMessages.EMAIL_ALREADY_EXIST.getCODE());
    }
}
