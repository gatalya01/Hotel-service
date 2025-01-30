package ru.skillbox.hotel_booking_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int maxOccupancy;

    // Список недоступных дат
    @ElementCollection
    @Builder.Default
    private List<LocalDate> unavailableDates = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @ToString.Exclude
    private Hotel hotel;


    public void addUnavailableDate(LocalDate date) {
        this.unavailableDates.add(date);
    }

    public void removeUnavailableDate(LocalDate date) {
        this.unavailableDates.remove(date);
    }

    public boolean isAvailable(LocalDate checkInDate, LocalDate checkOutDate) {
        for (LocalDate date : unavailableDates) {
            if (!date.isBefore(checkInDate) && !date.isAfter(checkOutDate)) {
                return false;
            }
        }
        return true;
    }
}