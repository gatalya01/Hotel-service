package ru.skillbox.hotel_booking_service.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import ru.skillbox.hotel_booking_service.entity.Hotel;
import ru.skillbox.hotel_booking_service.entity.Room;
import ru.skillbox.hotel_booking_service.service.HotelService;
import ru.skillbox.hotel_booking_service.web.model.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = HotelMapper.class)
public interface RoomMapper {

    @Mapping(target = "id", ignore = true)
    Room requestToRoom(UpsertRoomRequest request);

    Room requestToRoom(Long id, UpdateRoomRequest request);

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomResponse roomToResponse(Room room);

    @Mapping(target = "hotel", expression = "java(hotelMapper.responseToHotel(hotelService.findById(response.getHotelId())))")
    Room responseToRoom(RoomResponse response, @Context HotelService hotelService, @Context HotelMapper hotelMapper);

    default RoomListResponse roomListToRoomListResponse(Page<Room> roomPage) {
        RoomListResponse response = new RoomListResponse();
        response.setRooms(roomPage.getContent().stream()
                .map(this::roomToResponse)
                .toList());

        response.setTotalElements(roomPage.getTotalElements());
        response.setTotalPages(roomPage.getTotalPages());
        response.setCurrentPage(roomPage.getNumber());
        response.setPageSize(roomPage.getSize());
        return response;
    }
}