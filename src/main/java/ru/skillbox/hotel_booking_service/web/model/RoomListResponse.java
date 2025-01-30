package ru.skillbox.hotel_booking_service.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListResponse {

    private List<RoomResponse> rooms = new ArrayList<>();

    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}