package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.model.user.Role;
import com.moonlighthotel.hotelmanagementsystem.model.user.User;
import com.moonlighthotel.hotelmanagementsystem.validator.RoleValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@Component
public class UserBuilder {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final RoleValidator roleValidator;

    public User buildUserByAdmin(User user) {
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .roles(roleValidator.validateRoles(user.getRoles()))
                .created(Instant.now())
                .build();
    }

    public User buildUserByClient(User user, Role role) {
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .roles(Set.of(role))
                .created(Instant.now())
                .build();
    }

    public User buildUserForUpdate(Long id, User user) {
        return User.builder()
                .id(id)
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .roles(roleValidator.validateRoles(user.getRoles()))
                .created(Instant.now())
                .build();
    }
}
