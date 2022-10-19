package com.moonlighthotel.hotelmanagementsystem.api;

import com.moonlighthotel.hotelmanagementsystem.security.jwt.JwtAuthenticationRequest;

public class AuthenticationRequestCreator {

    public JwtAuthenticationRequest createAdminRequest() {
        return JwtAuthenticationRequest.builder()
                .email("j.riverdale@mail.com")
                .password("123456")
                .build();
    }

    public JwtAuthenticationRequest createClientRequest() {
        return JwtAuthenticationRequest.builder()
                .email("max_riviera@mail.com")
                .password("123456")
                .build();
    }
}
