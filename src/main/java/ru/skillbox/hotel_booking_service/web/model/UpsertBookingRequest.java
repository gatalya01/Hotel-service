package ru.skillbox.hotel_booking_service.web.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.skillbox.hotel_booking_service.validatioin.ValidBookingDates;

import java.time.LocalDate;

@Data
@ValidBookingDates
public class UpsertBookingRequest {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be future or present")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be future")
    private LocalDate checkOutDate;
}
