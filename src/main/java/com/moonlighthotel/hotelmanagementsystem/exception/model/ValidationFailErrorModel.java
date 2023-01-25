package com.moonlighthotel.hotelmanagementsystem.exception.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Builder
@Getter
public class ValidationFailErrorModel {

    private String message;
    private Map<String, Set<String>> errors;

    public ValidationFailErrorModel(String message, Map<String, Set<String>> errors) {
        this.message = message;
        this.errors = errors;
    }
}
