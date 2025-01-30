package ru.skillbox.hotel_booking_service.web.model;


import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateHotelRequest {

    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Size(min = 5, max = 150, message = "Announcement must be between 1 and 150 characters")
    private String announcement;

    @Size(min = 1, max = 100, message = "City must be between 1 and 100 characters")
    private String city;

    @Size(min = 5, max = 250, message = "Address must be between 1 and 250 characters")
    private String address;

    @PositiveOrZero(message = "Distance from center must be positive or zero")
    private double distanceFromCityCenter;
}