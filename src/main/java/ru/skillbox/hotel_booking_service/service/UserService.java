package ru.skillbox.hotel_booking_service.service;

import ru.skillbox.hotel_booking_service.entity.RoleType;
import ru.skillbox.hotel_booking_service.entity.User;
import ru.skillbox.hotel_booking_service.web.model.*;

public interface UserService {

    UserListResponse findAll(int page, int size);

    UserResponse findById(Long id);

    User findByUsername(String username);

    UserResponse save(UpsertUserRequest request, RoleType roleType);

    UserResponse update(Long id, UpdateUserRequest request, RoleType roleType);

    void deleteById(Long id);

    boolean existsByUsernameAndEmail(String username, String email);
}