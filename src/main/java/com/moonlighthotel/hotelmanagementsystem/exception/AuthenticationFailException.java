package com.moonlighthotel.hotelmanagementsystem.exception;

import lombok.Getter;

@Getter
public class AuthenticationFailException extends RuntimeException {

    private final String field;

    public AuthenticationFailException(String message, String field) {
        super(message);
        this.field = field;
    }
}
