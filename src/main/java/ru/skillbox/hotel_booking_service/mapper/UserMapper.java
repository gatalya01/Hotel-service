package ru.skillbox.hotel_booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import ru.skillbox.hotel_booking_service.entity.User;
import ru.skillbox.hotel_booking_service.web.model.*;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


    User requesttoUser(UpsertUserRequest request);

    User requesttoUser(Long id, UpdateUserRequest request);

    UserResponse userToResponse(User user);

    User responseToUser(UserResponse response);

    default UserListResponse userListToUserListResponse(Page<User> userPage) {
        UserListResponse response = new UserListResponse();
        response.setUsers(userPage.getContent().stream()
                .map(this::userToResponse)
                .toList());

        response.setTotalElements(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setCurrentPage(userPage.getNumber());
        response.setPageSize(userPage.getSize());
        return response;
    }
}