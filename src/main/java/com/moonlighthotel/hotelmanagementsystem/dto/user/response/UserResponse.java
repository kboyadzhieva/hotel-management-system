package com.moonlighthotel.hotelmanagementsystem.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserResponse {

    private Long id;

    private String email;

    private String name;

    private String surname;

    private String phone;

    private Set<String> roles;

    private String created;
}
