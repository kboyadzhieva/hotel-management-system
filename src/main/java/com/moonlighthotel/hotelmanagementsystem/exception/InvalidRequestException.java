package com.moonlighthotel.hotelmanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidRequestException extends RuntimeException {

    private final String requestField;

    public InvalidRequestException(String message, String requestField) {
        super(message);
        this.requestField = requestField;
    }
}
