package com.moonlighthotel.hotelmanagementsystem.security.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.AuthenticationFailException;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.User;
import com.moonlighthotel.hotelmanagementsystem.security.jwt.JwtAuthenticationRequest;
import com.moonlighthotel.hotelmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthenticationValidator {

    @Autowired
    private final UserService userService;

    @Autowired
    private final PasswordValidator passwordValidator;

    public void validateAuthenticationRequest(JwtAuthenticationRequest authenticationRequest) {
        validateAuthenticationRequestNotNull(authenticationRequest);

        try {
            User user = userService.findByEmail(authenticationRequest.getEmail());
            validateIfPasswordsMatch(authenticationRequest, user);
        } catch (RecordNotFoundException e) {
            throw new AuthenticationFailException("Incorrect email or password", "email or password");
        }
    }

    public void validateAuthenticationRequestNotNull(JwtAuthenticationRequest authenticationRequest) {
        if (authenticationRequest.getEmail() == null) {
            throw new AuthenticationFailException("Incorrect email or password", "email or password");
        } else if (authenticationRequest.getPassword() == null) {
            throw new AuthenticationFailException("Incorrect email or password", "email or password");
        }
    }

    public void validateIfPasswordsMatch(JwtAuthenticationRequest authenticationRequest, User user) {
        boolean doPasswordsMatch = passwordValidator.
                doPasswordsMatch(authenticationRequest.getPassword(), user.getPassword());

        if (!doPasswordsMatch) {
            throw new AuthenticationFailException("Incorrect email or password", "email or password");
        }
    }
}
