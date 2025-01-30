package ru.skillbox.hotel_booking_service.web.model;

import lombok.Data;


@Data
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
}