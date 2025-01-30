package ru.skillbox.hotel_booking_service.service;

import ru.skillbox.hotel_booking_service.web.model.*;


public interface BookingService {

    BookingResponse bookRoom(UpsertBookingRequest bookingRequest);

    BookingListResponse findAll(int page, int size);

    void deleteById(Long id);
}