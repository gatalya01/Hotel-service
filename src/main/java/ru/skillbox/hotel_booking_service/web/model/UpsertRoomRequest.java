package ru.skillbox.hotel_booking_service.web.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpsertRoomRequest {

    @NotBlank(message = "Name is required")

    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    private String description;

    @NotBlank(message = "Room number is required")
    @Size(min = 1, max = 10, message = "Room number must be between 1 and 10 characters")
    private String roomNumber;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @Min(value = 1, message = "Max occupancy must be at least 1")
    @Max(value = 100, message = "Max occupancy must be less than 100")
    private int maxOccupancy;

    private List<LocalDate> unavailableDates;
}