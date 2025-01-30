package ru.skillbox.hotel_booking_service.web.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class RoomResponse {

    private Long id;
    private String name;
    private String description;
    private String roomNumber;
    private BigDecimal price;
    private int maxOccupancy;
    private Long hotelId;
    private List<LocalDate> unavailableDates;
}