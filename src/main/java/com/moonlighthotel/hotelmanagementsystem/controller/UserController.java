package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.UserConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestCreate;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import com.moonlighthotel.hotelmanagementsystem.model.User;
import com.moonlighthotel.hotelmanagementsystem.service.UserService;
import com.moonlighthotel.hotelmanagementsystem.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Autowired
    private final UserValidator userValidator;

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

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserRequestCreate userRequestCreate,
                                             HttpServletRequest request) {
        User savedUser = null;
        boolean isAdmin = userValidator.isAdmin(request);

        if (isAdmin) {
            User user = userConverter.toUserByAdmin(userRequestCreate);
            savedUser = userService.saveByAdmin(user);
        } else {
            User user = userConverter.toUserByClient(userRequestCreate);
            savedUser = userService.saveByClient(user);
        }
        UserResponse userResponse = userConverter.toUserResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
