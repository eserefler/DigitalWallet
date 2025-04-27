package org.es.auth.exception;


import org.es.auth.domain.enums.ErrorMessages;

public class SamePasswordErrorException  extends RuntimeException{

    public SamePasswordErrorException(){
        super(ErrorMessages.SAME_PASSWORD_ERROR.getCODE());
    }
}