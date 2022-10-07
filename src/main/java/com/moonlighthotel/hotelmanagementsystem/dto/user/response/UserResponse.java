package com.moonlighthotel.hotelmanagementsystem.dto.user.response;

import lombok.Builder;

import java.util.Set;

@Builder
public class UserResponse {

    private Long id;

    private String email;

    private String name;

    private String surname;

    private String phone;

    private Set<String> roles;

    private String created;
}
