package com.moonlighthotel.hotelmanagementsystem.converter;

import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestCreate;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import com.moonlighthotel.hotelmanagementsystem.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Component
public class UserConverter {

    @Autowired
    private final RoleConverter roleConverter;

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .roles(roleConverter.substring(user.getRoles()))
                .created(DateTimeFormatter.ISO_INSTANT.format(user.getCreated()))
                .build();
    }

    public User toUserByAdmin(UserRequestCreate userRequestCreate) {
        return User.builder()
                .email(userRequestCreate.getEmail())
                .password(userRequestCreate.getPassword())
                .name(userRequestCreate.getName())
                .surname(userRequestCreate.getSurname())
                .phone(userRequestCreate.getPhone())
                .roles(roleConverter.toSetOfRoles(userRequestCreate.getRoles()))
                .created(Instant.now())
                .build();
    }

    public User toUserByClient(UserRequestCreate userRequestCreate) {
        return User.builder()
                .email(userRequestCreate.getEmail())
                .password(userRequestCreate.getPassword())
                .name(userRequestCreate.getName())
                .surname(userRequestCreate.getSurname())
                .phone(userRequestCreate.getPhone())
                .created(Instant.now())
                .build();
    }
}
