package ru.skillbox.hotel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import ru.skillbox.hotel_booking_service.entity.Hotel;
import ru.skillbox.hotel_booking_service.web.model.HotelListResponse;
import ru.skillbox.hotel_booking_service.web.model.HotelResponse;
import ru.skillbox.hotel_booking_service.web.model.UpdateHotelRequest;
import ru.skillbox.hotel_booking_service.web.model.UpsertHotelRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    Hotel requestToHotel(UpsertHotelRequest request);

    Hotel requestToHotel(Long id, UpdateHotelRequest request);

    Hotel responseToHotel(HotelResponse response);

    @Mapping(target = "reviewCount", source = "ratingCount")
    HotelResponse hotelToResponse(Hotel hotel);

    default HotelListResponse hotelListToHotelListResponse(Page<Hotel> hotelPage) {
        HotelListResponse response = new HotelListResponse();
        response.setHotels(hotelPage.getContent().stream()
                .map(this::hotelToResponse)
                .toList());

        response.setTotalElements(hotelPage.getTotalElements());
        response.setTotalPages(hotelPage.getTotalPages());
        response.setCurrentPage(hotelPage.getNumber());
        response.setPageSize(hotelPage.getSize());
        return response;
    }


}