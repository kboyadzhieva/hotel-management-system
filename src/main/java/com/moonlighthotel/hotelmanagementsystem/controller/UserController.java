package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.UserConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import com.moonlighthotel.hotelmanagementsystem.model.User;
import com.moonlighthotel.hotelmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestMapping(value = "/users")
@RestController
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserConverter userConverter;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userService.findAll();
        List<UserResponse> userResponseList = users.stream()
                .map(userConverter::toUserResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userResponseList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        UserResponse userResponse = userConverter.toUserResponse(user);

        return ResponseEntity.ok(userResponse);
    }
}
