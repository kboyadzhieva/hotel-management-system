package com.moonlighthotel.hotelmanagementsystem.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationFailException extends RuntimeException {

    private final String field;

    public AuthenticationFailException(String message, String field) {
        super(message);
        this.field = field;
    }
}
