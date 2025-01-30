package ru.skillbox.hotel_booking_service.web.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UpdateUserRequest {

    @Size(min = 1, max = 150, message = "Username must be between 1 and 150 characters")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Email(message = "Email should be valid")
    private String email;
}