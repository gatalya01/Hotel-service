package ru.skillbox.hotel_booking_service.web.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.hotel_booking_service.validatioin.RoomFilterValid;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RoomFilterValid
public class RoomFilter {

    @Positive(message = "Page size must be a positive number")
    private Integer size;

    @PositiveOrZero(message = "Page number must be zero or a positive number")
    private Integer page;

    @Positive(message = "ID must be a positive number")
    private Long id;

    @Size(max = 255, message = "Room name cannot exceed 255 characters")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum price must be greater than 0")
    private Double minPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum price must be greater than 0")
    @DecimalMax(value = "1000000.0", message = "Maximum price must be less than 1,000,000")
    private Double maxPrice;

    @Positive(message = "Maximum occupancy must be a positive number")
    private Integer maxOccupancy;

    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Positive(message = "Hotel ID must be a positive number")
    private Long hotelId;
}
