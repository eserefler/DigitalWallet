package org.es.auth.exception;

import org.es.auth.domain.enums.ErrorMessages;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super(ErrorMessages.USER_ALREADY_EXIST.getCODE());
    }
}
