package ru.skillbox.hotel_booking_service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.hotel_booking_service.entity.RoleType;
import ru.skillbox.hotel_booking_service.service.HotelService;
import ru.skillbox.hotel_booking_service.service.UserService;
import ru.skillbox.hotel_booking_service.web.model.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<UserListResponse> findAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(userService.findAll(page, size));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok((userService.findById(id)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request, @RequestParam RoleType roleType) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(request, roleType));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateUserRequest request,
                                               @RequestParam(required = false) RoleType roleType) {

        return ResponseEntity.ok(userService.update(id, request, roleType));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}