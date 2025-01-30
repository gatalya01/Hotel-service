package ru.skillbox.hotel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import ru.skillbox.hotel_booking_service.entity.Booking;
import ru.skillbox.hotel_booking_service.web.model.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    Booking requestToBooking(UpsertBookingRequest request);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "bookingId")
    BookingResponse bookingToResponse(Booking booking);

    default BookingListResponse bookingListToBookingListResponse(Page<Booking> bookingPage) {
        BookingListResponse response = new BookingListResponse();
        response.setBookings(bookingPage.getContent().stream()
                .map(this::bookingToResponse)
                .toList());

        response.setTotalElements(bookingPage.getTotalElements());
        response.setTotalPages(bookingPage.getTotalPages());
        response.setCurrentPage(bookingPage.getNumber());
        response.setPageSize(bookingPage.getSize());
        return response;
    }
}