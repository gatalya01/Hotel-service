package ru.skillbox.hotel_booking_service.validatioin;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoomFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomFilterValid {
    String message() default "Both 'page' and 'size' must be specified for pagination, and at least one filter field" +
            " (id, name, minPrice, maxPrice, maxOccupancy, checkInDate, checkOutDate, hotelId) must be provided." +
            " Additionally, both 'checkInDate' and 'checkOutDate' must be provided together.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
