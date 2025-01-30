package ru.skillbox.hotel_booking_service.validatioin;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookingDatesValidator.class)
@Documented
public @interface ValidBookingDates {
    String message() default "Check-out date must be after check-in date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
