package org.es.auth.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    USER_NOT_FOUND("Customer not found.", "ERR100"),
    INVALID_PASSWORD("Invalid password.","ERR101"),
    USER_ALREADY_EXIST("Customer already exist.","ERR102"),
    EMAIL_ALREADY_EXIST("Email already exist.","ERR103"),
    PHONE_ALREADY_EXIST("Phone already exist.","ERR104"),
    SAME_PASSWORD_ERROR("Same Password can not be.","ERR105"),
    TOKEN_NOT_VALIDATED("Token Not validated.","ERR200"),
    UNAUTHORIZED("Unauthorized.","ERR201"),
    INVALID_TOKEN("Invalid token.","ERR202"),
    EXPIRED_TOKEN("Expired token.","ERR203");


    private final String MESSAGE;
    private final String CODE;
}
