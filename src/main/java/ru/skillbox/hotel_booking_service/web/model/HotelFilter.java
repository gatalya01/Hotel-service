package ru.skillbox.hotel_booking_service.web.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.hotel_booking_service.validatioin.HotelFilterValid;

@Data
@NoArgsConstructor
@HotelFilterValid
public class HotelFilter {

    @Positive(message = "Page size must be a positive number")
    private Integer size;

    @PositiveOrZero(message = "Page number must be zero or a positive number")
    private Integer page;

    @Positive(message = "Hotel ID must be a positive number")
    private Long id;

    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Size(min = 5, max = 150, message = "Announcement must be between 5 and 150 characters")
    private String announcement;

    @Size(min = 1, max = 100, message = "City must be between 1 and 100 characters")
    private String city;

    @Size(min = 5, max = 250, message = "Address must be between 5 and 250 characters")
    private String address;

    @PositiveOrZero(message = "Distance from center must be positive or zero")
    private Double distanceFromCityCenter;

    @DecimalMin(value = "0.0", message = "Rating cannot be less than 0")
    @DecimalMax(value = "5.0", message = "Rating cannot be more than 5")
    private Double rating;  // Изменено на класс-обёртку

    @Min(value = 0, message = "Number of ratings cannot be negative")
    private Integer reviewCount;
}