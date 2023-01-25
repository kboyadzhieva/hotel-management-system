package com.moonlighthotel.hotelmanagementsystem.exception;

import lombok.Getter;

@Getter
public class DuplicateRecordException extends RuntimeException {

    private final String field;

    public DuplicateRecordException(String message, String field) {
        super(message);
        this.field = field;
    }
}
