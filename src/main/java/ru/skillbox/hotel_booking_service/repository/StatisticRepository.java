package ru.skillbox.hotel_booking_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skillbox.hotel_booking_service.entity.Statistic;

public interface StatisticRepository extends MongoRepository<Statistic, String> {
}
