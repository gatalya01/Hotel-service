package ru.skillbox.hotel_booking_service.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.hotel_booking_service.entity.Hotel;
import ru.skillbox.hotel_booking_service.web.model.HotelFilter;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter hotelFilter) {
        return Specification.where(byId(hotelFilter.getId()))
                .and(byName(hotelFilter.getName()))
                .and(byAnnouncement(hotelFilter.getAnnouncement()))
                .and(byCity(hotelFilter.getCity()))
                .and(byAddress(hotelFilter.getAddress()))
                .and(byDistanceFromCityCenter(hotelFilter.getDistanceFromCityCenter()))
                .and(byRating(hotelFilter.getRating()))
                .and(byRatingCount(hotelFilter.getReviewCount()));
    }

    static Specification<Hotel> byRating(Double rating) {
        return (root, query, criteriaBuilder) -> {
            if (rating == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("rating"), rating);
        };
    }

    static Specification<Hotel> byRatingCount(Integer ratingCount) {
        return (root, query, criteriaBuilder) -> {
            if (ratingCount == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("ratingCount"), ratingCount);
        };
    }

    static Specification<Hotel> byId(Long hotelId) {
        return (root, query, criteriaBuilder) -> {
            if (hotelId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), hotelId);
        };
    }

    static Specification<Hotel> byDistanceFromCityCenter(Double distanceFromCityCenter) {
        return (root, query, criteriaBuilder) -> {
            if (distanceFromCityCenter == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("distanceFromCityCenter"), distanceFromCityCenter);
        };
    }

    static Specification<Hotel> byAnnouncement(String announcement) {
        return (root, query, criteriaBuilder) -> {
            if (announcement == null || announcement.trim().isEmpty()) {
                return null;
            }
            // Используем like для поиска по подстроке
            return criteriaBuilder.like(root.get("announcement"), "%" + announcement + "%");
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.trim().isEmpty()) {
                return null;
            }
            // Используем like для частичного совпадения
            return criteriaBuilder.like(root.get("city"), "%" + city + "%");
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, criteriaBuilder) -> {
            if (address == null || address.trim().isEmpty()) {
                return null;
            }
            // Используем like для частичного совпадения
            return criteriaBuilder.like(root.get("address"), "%" + address + "%");
        };
    }

    static Specification<Hotel> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            // Используем like для частичного совпадения
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }
}