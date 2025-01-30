package ru.skillbox.hotel_booking_service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.hotel_booking_service.service.RoomService;
import ru.skillbox.hotel_booking_service.web.model.*;


@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok((roomService.findById(id)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid UpsertRoomRequest request, @RequestParam("hotelId") Long hotelId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomService.save(request, hotelId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateRoomRequest request) {

        return ResponseEntity.ok(roomService.update(id, request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<RoomListResponse> filterBy(@Valid RoomFilter filter) {
        return ResponseEntity.ok(roomService.filterBy(filter));
    }
}