package com.driver.myvehiclelogger.web.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;

    @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters")
    @Nullable
    private String firstName;

    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters")
    @Nullable
    private String lastName;

    @Nullable
    @Size(min = 6, max = 12, message = "Phone number must be between 6 and 12 digits")
    private String phoneNumber;
}
