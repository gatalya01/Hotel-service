package ru.skillbox.hotel_booking_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hotel name cannot be empty")
    @Size(max = 100, message = "Hotel name cannot exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Announcement cannot exceed 255 characters")
    private String announcement;

    @NotBlank(message = "City cannot be empty")
    @Size(max = 50, message = "City name cannot exceed 50 characters")
    private String city;

    @NotBlank(message = "Address cannot be empty")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @Positive(message = "Distance from city center must be a positive number")
    private double distanceFromCityCenter;

    @DecimalMin(value = "0.0", message = "Rating cannot be less than 0")
    @DecimalMax(value = "5.0", message = "Rating cannot be more than 5")
    private double rating;

    @Min(value = 0, message = "Number of ratings cannot be negative")
    private int ratingCount;
}

