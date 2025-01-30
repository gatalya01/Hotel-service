package ru.skillbox.hotel_booking_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationEvent {
    private Long userId;

    private LocalDateTime eventDateTime;
}