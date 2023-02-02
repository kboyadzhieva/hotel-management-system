package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.UserConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestCreate;
import com.moonlighthotel.hotelmanagementsystem.dto.user.request.UserRequestUpdate;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import com.moonlighthotel.hotelmanagementsystem.exception.model.AuthenticationFailErrorModel;
import com.moonlighthotel.hotelmanagementsystem.exception.model.RecordNotFoundErrorModel;
import com.moonlighthotel.hotelmanagementsystem.exception.model.ValidationFailErrorModel;
import com.moonlighthotel.hotelmanagementsystem.model.User;
import com.moonlighthotel.hotelmanagementsystem.service.UserService;
import com.moonlighthotel.hotelmanagementsystem.swagger.SwaggerConfiguration;
import com.moonlighthotel.hotelmanagementsystem.validator.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = SwaggerConfiguration.USER_TAG)
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserConverter userConverter;

    @Autowired
    private final UserValidator userValidator;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get users List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userService.findAll();
        List<UserResponse> userResponseList = users.stream()
                .map(userConverter::toUserResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userResponseList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<UserResponse> findById(@Parameter(description = "User ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {
        User user = userService.findById(id);
        UserResponse userResponse = userConverter.toUserResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    @Operation(summary = "Create a new user (as admin) or register a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))})})
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
    @PutMapping(value = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a User by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<UserResponse> update(@Parameter(description = "User ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                               @RequestBody @Valid UserRequestUpdate userRequestUpdate) {
        User user = userConverter.toUserByAdmin(id, userRequestUpdate);
        User updatedUser = userService.update(id, user);
        UserResponse userResponse = userConverter.toUserResponse(updatedUser);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Remove a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<HttpStatus> deleteById(@Parameter(description = "User ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
