package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.security.encoder.JwtPasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PasswordValidator {

    @Autowired
    private final JwtPasswordEncoder passwordEncoder;

    public Boolean doPasswordsMatch(String password, String encodedPassword) {
        return passwordEncoder.getPasswordEncoder().matches(password, encodedPassword);
    }
}
