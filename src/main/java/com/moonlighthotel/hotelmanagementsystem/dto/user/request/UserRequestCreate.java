package com.moonlighthotel.hotelmanagementsystem.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserRequestCreate {

    @NotBlank(message = "Enter an email address")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Enter a password")
    @Length(min = 6, max = 255)
    private String password;

    @NotBlank(message = "Enter first name")
    @Length(min = 2, max = 255)
    private String name;

    @NotBlank(message = "Enter last name")
    @Length(min = 2, max = 255)
    private String surname;

    @NotBlank(message = "Enter a phone number")
    @Length(min = 7, max = 15)
    private String phone;

    private Set<String> roles;
}
