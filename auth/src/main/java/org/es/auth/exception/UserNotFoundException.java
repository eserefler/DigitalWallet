package org.es.auth.exception;

import org.es.auth.domain.enums.ErrorMessages;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super(ErrorMessages.USER_NOT_FOUND.getCODE());
    }
}
