package ru.skillbox.hotel_booking_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skillbox.hotel_booking_service.entity.Hotel;
import ru.skillbox.hotel_booking_service.entity.Room;
import ru.skillbox.hotel_booking_service.exception.EntityNotFoundException;
import ru.skillbox.hotel_booking_service.mapper.HotelMapper;
import ru.skillbox.hotel_booking_service.mapper.RoomMapper;
import ru.skillbox.hotel_booking_service.repository.HotelSpecification;
import ru.skillbox.hotel_booking_service.repository.RoomRepository;
import ru.skillbox.hotel_booking_service.repository.RoomSpecification;
import ru.skillbox.hotel_booking_service.service.HotelService;
import ru.skillbox.hotel_booking_service.service.RoomService;
import ru.skillbox.hotel_booking_service.utils.BeanUtils;
import ru.skillbox.hotel_booking_service.web.model.*;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelService hotelService;
    private final HotelMapper hotelMapper;


    @Override
    public RoomResponse findById(Long id) {

        return roomMapper.roomToResponse(roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Room with ID {0} not found", id))));
    }

    @Override
    public RoomResponse save(UpsertRoomRequest request, Long hotelId) {

        Hotel hotel = hotelMapper.responseToHotel(hotelService.findById(hotelId));

        Room room = roomMapper.requestToRoom(request);

        room.setHotel(hotel);

        return roomMapper.roomToResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse update(Long id, UpdateRoomRequest request) {

        Room existedRoom = roomMapper.responseToRoom(findById(id), hotelService, hotelMapper);
        Room updatedRoom = roomMapper.requestToRoom(id, request);

        BeanUtils.copyRoomNotNullProperties(updatedRoom, existedRoom);

        return roomMapper.roomToResponse(roomRepository.save(existedRoom));
    }


    @Override
    public void deleteById(Long id) {
        findById(id);
        roomRepository.deleteById(id);
    }

    @Override
    public RoomListResponse filterBy(RoomFilter filter) {
        return roomMapper.roomListToRoomListResponse(roomRepository.findAll(RoomSpecification.withFilter(filter),
                PageRequest.of(filter.getPage(), filter.getSize())));
    }
}