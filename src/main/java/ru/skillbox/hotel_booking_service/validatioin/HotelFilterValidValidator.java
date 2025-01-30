package ru.skillbox.hotel_booking_service.validatioin;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import ru.skillbox.hotel_booking_service.web.model.HotelFilter;

public class HotelFilterValidValidator implements ConstraintValidator<HotelFilterValid, HotelFilter> {

    @Override
    public boolean isValid(HotelFilter value, ConstraintValidatorContext context) {

        if (ObjectUtils.anyNull(value.getPage(), value.getSize())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Both page and size must be specified.")
                    .addConstraintViolation();
            return false;
        }


        if (ObjectUtils.allNull(value.getId(), value.getName(), value.getCity(), value.getAddress(),
                value.getAnnouncement(), value.getReviewCount(), value.getRating(), value.getDistanceFromCityCenter())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("At least one of must be specified:" +
                            " id, name, city, address, announcement, rating, ratingCount, distanceFromCityCenter!.")
                    .addConstraintViolation();
            return false;
        }


        return true;
    }
}

