package ru.skillbox.hotel_booking_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.hotel_booking_service.entity.Booking;
import ru.skillbox.hotel_booking_service.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(Room room, LocalDate checkOutDate, LocalDate checkInDate);
}