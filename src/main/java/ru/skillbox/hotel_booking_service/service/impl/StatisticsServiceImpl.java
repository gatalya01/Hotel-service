package ru.skillbox.hotel_booking_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.skillbox.hotel_booking_service.entity.EventType;
import ru.skillbox.hotel_booking_service.entity.Statistic;
import ru.skillbox.hotel_booking_service.events.RoomBookingEvent;
import ru.skillbox.hotel_booking_service.events.UserRegistrationEvent;
import ru.skillbox.hotel_booking_service.repository.StatisticRepository;
import ru.skillbox.hotel_booking_service.service.StatisticsService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    @Value("${app.kafka.roomBookings}")
    private String roomBookingsTopic;

    @Value("${app.kafka.userRegistrations}")
    private String userRegistrationsTopic;

    @Value("${app.kafka.groupId}")
    private String groupId;

    private final StatisticRepository statisticRepository;

    @KafkaListener(topics = "${app.kafka.userRegistrations}",
            groupId = "${app.kafka.groupId}",
            containerFactory = "userRegistrationEventKafkaListenerContainerFactory")
    public void handleUserRegistration(UserRegistrationEvent event) {
        Statistic statistic = new Statistic();
        statistic.setUserId(event.getUserId());
        statistic.setEventType(EventType.USER_REGISTRATION);
        statistic.setEventDateAndTime(event.getEventDateTime());
        statisticRepository.save(statistic);
    }


    @KafkaListener(topics = "${app.kafka.roomBookings}",
            groupId = "${app.kafka.groupId}",
            containerFactory = "roomBookingEventKafkaListenerContainerFactory")
    public void handleRoomBooking(RoomBookingEvent event) {
        Statistic statistic = new Statistic();
        statistic.setUserId(event.getUserId());
        statistic.setRoomId(event.getRoomId());
        statistic.setCheckInDate(event.getCheckInDate());
        statistic.setCheckOutDate(event.getCheckOutDate());
        statistic.setEventType(EventType.ROOM_BOOKING);
        statistic.setEventDateAndTime(event.getEventDateTime());
        statisticRepository.save(statistic);
    }

    @Override
    public String exportStatisticsToCSV() throws IOException {

        List<Statistic> statistics = statisticRepository.findAll();
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("User ID, Room ID, Check In Date, Check Out Date, Event Type, Event DateTime\n");

        for (Statistic statistic : statistics) {
            csvBuilder.append(statistic.getUserId() != null ? statistic.getUserId().toString() : "-")
                    .append(",")
                    .append(statistic.getRoomId() != null ? statistic.getRoomId().toString() : "-")
                    .append(",")
                    .append(statistic.getCheckInDate() != null ? statistic.getCheckInDate().toString() : "-")
                    .append(",")
                    .append(statistic.getCheckOutDate() != null ? statistic.getCheckOutDate().toString() : "-")
                    .append(",")
                    .append(statistic.getEventType() != null ? statistic.getEventType().toString() : "-")
                    .append(",")
                    .append(statistic.getEventDateAndTime() != null ? statistic.getEventDateAndTime().toString() : "-")
                    .append("\n");
        }

        return csvBuilder.toString();
    }

}
