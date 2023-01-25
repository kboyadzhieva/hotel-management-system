package com.moonlighthotel.hotelmanagementsystem.exception.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Builder
@Getter
public class AuthenticationFailErrorModel {

    private final String message;
    private final Map<String, Set<String>> errors;

    public AuthenticationFailErrorModel(String message, Map<String, Set<String>> errors) {
        this.message = message;
        this.errors = errors;
    }
}
