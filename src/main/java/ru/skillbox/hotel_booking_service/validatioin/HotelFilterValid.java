package ru.skillbox.hotel_booking_service.validatioin;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HotelFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HotelFilterValid {
    String message() default "Pagination fields must be specified, and at least one of the following" +
            " values must be provided: id, name, city, address, announcement, rating, ratingCount, distanceFromCityCenter!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}