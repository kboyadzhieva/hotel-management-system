package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.user.UserConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import com.moonlighthotel.hotelmanagementsystem.exception.model.AuthenticationFailErrorModel;
import com.moonlighthotel.hotelmanagementsystem.model.user.User;
import com.moonlighthotel.hotelmanagementsystem.security.JwtUserDetailsService;
import com.moonlighthotel.hotelmanagementsystem.security.jwt.JwtAuthenticationRequest;
import com.moonlighthotel.hotelmanagementsystem.security.jwt.JwtAuthenticationResponse;
import com.moonlighthotel.hotelmanagementsystem.security.jwt.JwtUtil;
import com.moonlighthotel.hotelmanagementsystem.security.validator.AuthenticationValidator;
import com.moonlighthotel.hotelmanagementsystem.service.UserService;
import com.moonlighthotel.hotelmanagementsystem.swagger.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = SwaggerConfiguration.USER_TAG)
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/users/token", method = RequestMethod.POST)
    @Operation(summary = "Obtain a JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationFailErrorModel.class))})})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        authenticationValidator.validateAuthenticationRequest(authenticationRequest);
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        User user = userService.findByEmail(authenticationRequest.getEmail());
        UserResponse userResponse = userConverter.toUserResponse(user);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token, userResponse));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

