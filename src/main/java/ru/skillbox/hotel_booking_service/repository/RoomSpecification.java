package ru.skillbox.hotel_booking_service.repository;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.hotel_booking_service.entity.Room;
import ru.skillbox.hotel_booking_service.web.model.RoomFilter;

import java.time.LocalDate;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter roomFilter) {
        return Specification.where(byId(roomFilter.getId()))
                .and(byName(roomFilter.getName()))
                .and(byPriceRange(roomFilter.getMinPrice(), roomFilter.getMaxPrice()))
                .and(byGuestsCount(roomFilter.getMaxOccupancy()))
                .and(byHotelId(roomFilter.getHotelId()))
                .and(availableInDateRange(roomFilter.getCheckInDate(), roomFilter.getCheckOutDate()));
    }

    static Specification<Room> byId(Long roomId) {
        return (root, query, criteriaBuilder) -> {
            if (roomId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), roomId);
        };
    }

    static Specification<Room> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    static Specification<Room> byPriceRange(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            return null;
        };
    }

    static Specification<Room> byGuestsCount(Integer maxOccupancy) {
        return (root, query, criteriaBuilder) -> {
            if (maxOccupancy == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("maxOccupancy"), maxOccupancy);
        };
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return (root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("hotel").get("id"), hotelId);
        };
    }


    static Specification<Room> availableInDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        return (root, query, criteriaBuilder) -> {
            if (checkInDate == null || checkOutDate == null) {
                return null;
            }

            // Проверяем, что ни одна из недоступных дат не попадает в диапазон заезда-выезда
            return criteriaBuilder.and(
                    criteriaBuilder.isNotMember(checkInDate, root.get("unavailableDates")),
                    criteriaBuilder.isNotMember(checkOutDate, root.get("unavailableDates"))
            );
        };
    }
}