package ru.skillbox.hotel_booking_service.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "statistics")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic {
    @Id
    private String id;
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private EventType eventType;
    private LocalDateTime eventDateAndTime;
}