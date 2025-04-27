package org.es.auth.exception;


import org.es.auth.domain.enums.ErrorMessages;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super(ErrorMessages.INVALID_PASSWORD.getCODE());
    }
}
