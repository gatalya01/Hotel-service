package ru.skillbox.hotel_booking_service.service;

import org.apache.kafka.common.protocol.types.Field;
import ru.skillbox.hotel_booking_service.events.RoomBookingEvent;
import ru.skillbox.hotel_booking_service.events.UserRegistrationEvent;

import java.io.IOException;

public interface StatisticsService {

    void handleUserRegistration(UserRegistrationEvent event);

    void handleRoomBooking(RoomBookingEvent event);

    String exportStatisticsToCSV() throws IOException;
}