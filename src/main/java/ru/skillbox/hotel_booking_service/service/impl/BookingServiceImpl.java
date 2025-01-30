package ru.skillbox.hotel_booking_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.hotel_booking_service.entity.Booking;
import ru.skillbox.hotel_booking_service.entity.Room;
import ru.skillbox.hotel_booking_service.entity.User;
import ru.skillbox.hotel_booking_service.events.RoomBookingEvent;
import ru.skillbox.hotel_booking_service.exception.EntityNotFoundException;
import ru.skillbox.hotel_booking_service.mapper.BookingMapper;
import ru.skillbox.hotel_booking_service.mapper.HotelMapper;
import ru.skillbox.hotel_booking_service.mapper.RoomMapper;
import ru.skillbox.hotel_booking_service.mapper.UserMapper;
import ru.skillbox.hotel_booking_service.repository.BookingRepository;
import ru.skillbox.hotel_booking_service.repository.RoomRepository;
import ru.skillbox.hotel_booking_service.service.BookingService;
import ru.skillbox.hotel_booking_service.service.HotelService;
import ru.skillbox.hotel_booking_service.service.RoomService;
import ru.skillbox.hotel_booking_service.service.UserService;
import ru.skillbox.hotel_booking_service.web.model.BookingListResponse;
import ru.skillbox.hotel_booking_service.web.model.BookingResponse;
import ru.skillbox.hotel_booking_service.web.model.UpsertBookingRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final RoomService roomService;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomMapper roomMapper;
    private final UserMapper userMapper;
    private final HotelService hotelService;
    private final HotelMapper hotelMapper;
    private final RoomRepository roomRepository;
    private final KafkaTemplate<String, RoomBookingEvent> kafkaTemplate;

    @Value("${app.kafka.roomBookings}")
    private String roomBookingTopic;

    @Override
    public BookingResponse bookRoom(UpsertBookingRequest request) {

        Room room = roomMapper.responseToRoom(roomService.findById(request.getRoomId()), hotelService, hotelMapper);

        User user = userMapper.responseToUser(userService.findById(request.getUserId()));

        if (!room.isAvailable(request.getCheckInDate(), request.getCheckOutDate())) {
            throw new IllegalArgumentException("Room is not available for the selected dates.");
        }

        Booking booking = bookingMapper.requestToBooking(request);

        booking.setRoom(room);
        booking.setUser(user);

        Booking savedBooking = bookingRepository.save(booking);

        addUnavailableDatesForRoom(room, request.getCheckInDate(), request.getCheckOutDate());

        RoomBookingEvent event = new RoomBookingEvent(user.getId(), room.getId(), booking.getCheckInDate(), booking.getCheckOutDate(),
                LocalDateTime.now());
        log.info("Saved room booking event: {}", event);
        kafkaTemplate.send(roomBookingTopic, event);

        return bookingMapper.bookingToResponse(savedBooking);
    }

    @Override
    public BookingListResponse findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingMapper.bookingListToBookingListResponse(bookingRepository.findAll(pageable));
    }

    @Override
    public void deleteById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        Room room = booking.getRoom();

        List<LocalDate> datesToRemove = booking.getCheckInDate().datesUntil(booking.getCheckOutDate().plusDays(1)) // Включаем checkOutDate
                .toList();

        room.getUnavailableDates().removeAll(datesToRemove);

        roomRepository.save(room);

        bookingRepository.deleteById(id);
    }

    private void addUnavailableDatesForRoom(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        LocalDate date = checkInDate;
        while (!date.isAfter(checkOutDate)) {
            room.addUnavailableDate(date);
            date = date.plusDays(1);
        }
        roomRepository.save(room);
    }
}
