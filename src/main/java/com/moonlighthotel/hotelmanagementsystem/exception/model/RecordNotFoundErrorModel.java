package com.moonlighthotel.hotelmanagementsystem.exception.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecordNotFoundErrorModel {

    private final String message;

    public RecordNotFoundErrorModel(String message) {
        this.message = message;
    }
}
