package ru.skillbox.hotel_booking_service.validatioin;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.skillbox.hotel_booking_service.web.model.UpsertBookingRequest;


public class BookingDatesValidator implements ConstraintValidator<ValidBookingDates, UpsertBookingRequest> {

    @Override
    public boolean isValid(UpsertBookingRequest request, ConstraintValidatorContext context) {
        if (request.getCheckInDate() == null || request.getCheckOutDate() == null) {
            return true;
        }

        return request.getCheckInDate().isBefore(request.getCheckOutDate());
    }
}
