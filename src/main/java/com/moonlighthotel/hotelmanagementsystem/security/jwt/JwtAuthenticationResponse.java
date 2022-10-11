package com.moonlighthotel.hotelmanagementsystem.security.jwt;

import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtAuthenticationResponse {

    private String token;

    private UserResponse user;
}
