package ru.skillbox.hotel_booking_service.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingListResponse {

    private List<BookingResponse> bookings = new ArrayList<>();
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
