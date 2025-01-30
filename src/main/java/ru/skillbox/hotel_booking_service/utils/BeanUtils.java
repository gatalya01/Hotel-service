package ru.skillbox.hotel_booking_service.utils;

import lombok.experimental.UtilityClass;
import ru.skillbox.hotel_booking_service.entity.Hotel;
import ru.skillbox.hotel_booking_service.entity.Room;
import ru.skillbox.hotel_booking_service.entity.User;

@UtilityClass
public class BeanUtils {


    public void copyHotelNotNullProperties(Hotel source, Hotel destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination must not be null");
        }

        if (source.getId() != null) {
            destination.setId(source.getId());
        }

        if (source.getName() != null) {
            destination.setName(source.getName());
        }

        if (source.getAnnouncement() != null) {
            destination.setAnnouncement(source.getAnnouncement());
        }

        if (source.getCity() != null) {
            destination.setCity(source.getCity());
        }

        if (source.getAddress() != null) {
            destination.setAddress(source.getAddress());
        }

        if (source.getDistanceFromCityCenter() >= 0) {
            destination.setDistanceFromCityCenter(source.getDistanceFromCityCenter());
        }
    }


    public void copyRoomNotNullProperties(Room source, Room destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination must not be null");
        }

        if (source.getId() != null) {
            destination.setId(source.getId());
        }

        if (source.getName() != null) {
            destination.setName(source.getName());
        }

        if (source.getRoomNumber() != null) {
            destination.setRoomNumber(source.getRoomNumber());
        }

        if (source.getDescription() != null) {
            destination.setDescription(source.getDescription());
        }

        if (source.getPrice() != null) {
            destination.setPrice(source.getPrice());
        }


        if (source.getUnavailableDates() != null && !source.getUnavailableDates().isEmpty()) {
            destination.setUnavailableDates(source.getUnavailableDates());
        }

        if (source.getHotel() != null) {
            destination.setHotel(source.getHotel());
        }

        if (source.getMaxOccupancy() > 0) {
            destination.setMaxOccupancy(source.getMaxOccupancy());
        }
    }

    public void copyUserNotNullProperties(User source, User destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination must not be null");
        }

        if (source.getId() != null) {
            destination.setId(source.getId());
        }

        if (source.getUsername() != null) {
            destination.setUsername(source.getUsername());
        }

        if (source.getPassword() != null) {
            destination.setPassword(source.getPassword());
        }

        if (source.getEmail() != null) {
            destination.setEmail(source.getEmail());
        }

        if (source.getRole() != null) {
            destination.setRole(source.getRole());
        }
    }
}