package ru.skillbox.hotel_booking_service.web.model;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateRoomRequest {

    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    private String description;

    @Size(min = 1, max = 10, message = "Room number must be between 1 and 10 characters")
    private String roomNumber;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    private int maxOccupancy;

    private List<LocalDate> unavailableDates;
}