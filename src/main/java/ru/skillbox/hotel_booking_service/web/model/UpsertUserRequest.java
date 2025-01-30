package ru.skillbox.hotel_booking_service.web.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpsertUserRequest {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 1, max = 150, message = "Username must be between 1 and 150 characters")
    private String username;

    @NotBlank(message = "Password is mandatory!")
    @Size(min = 8, max = 30, message = "Password length min: {min} max: {max}!")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
}