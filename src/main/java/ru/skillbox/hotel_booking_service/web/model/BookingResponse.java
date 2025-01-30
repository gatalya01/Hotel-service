package ru.skillbox.hotel_booking_service.web.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
    private Long userId;
}