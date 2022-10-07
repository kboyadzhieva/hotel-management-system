package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.model.Role;
import com.moonlighthotel.hotelmanagementsystem.model.User;
import com.moonlighthotel.hotelmanagementsystem.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserBuilder {

    @Autowired
    private final RoleService roleService;

    public User buildUserByAdmin(User user) {
        return User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .roles(user.getRoles().stream()
                        .map(role -> roleService.findByName("ROLE" + role.getName().toUpperCase()))
                        .collect(Collectors.toSet()))
                .created(Instant.now())
                .build();
    }

    public User buildUserByClient(User user, Role role) {
        return User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .roles(Set.of(role))
                .created(Instant.now())
                .build();
    }

    public User buildUserForUpdate(User user, Long id) {
        return User.builder()
                .id(id)
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .roles(user.getRoles().stream()
                        .map(role -> roleService.findByName("ROLE" + role.getName().toUpperCase()))
                        .collect(Collectors.toSet()))
                .created(Instant.now())
                .build();
    }
}
