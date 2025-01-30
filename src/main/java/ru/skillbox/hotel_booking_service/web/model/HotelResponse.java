package ru.skillbox.hotel_booking_service.web.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HotelResponse {

    private Long id;
    private String name;
    private String announcement;
    private String city;

    private String address;

    //Для обеспечения целостности данных
    @Positive(message = "Distance from center must be positive")
    private double distanceFromCityCenter;

    //Для обеспечения целостности данных
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private double rating;

    //Для обеспечения целостности данных
    @Positive(message = "Review counts must be positive")
    private int reviewCount;
}