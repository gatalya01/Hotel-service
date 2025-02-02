package ru.skillbox.hotel_booking_service.tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skillbox.hotel_booking_service.service.UserService;
import ru.skillbox.hotel_booking_service.web.controller.UserController;
import ru.skillbox.hotel_booking_service.web.model.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @Test
  void findAll_ShouldReturnUserList() {
    UserListResponse userListResponse = new UserListResponse();
    when(userService.findAll(0, 10)).thenReturn(userListResponse);

    ResponseEntity<UserListResponse> response = userController.findAll(0, 10);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void findById_ShouldReturnUser() {
    UserResponse userResponse = new UserResponse();
    when(userService.findById(1L)).thenReturn(userResponse);

    ResponseEntity<UserResponse> response = userController.findById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void create_ShouldReturnCreatedUser() {
    UpsertUserRequest request = new UpsertUserRequest();
    UserResponse userResponse = new UserResponse();
    when(userService.save(eq(request), any())).thenReturn(userResponse);

    ResponseEntity<UserResponse> response = userController.create(request, null);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void update_ShouldReturnUpdatedUser() {
    UpdateUserRequest request = new UpdateUserRequest();
    UserResponse userResponse = new UserResponse();
    when(userService.update(eq(1L), eq(request), any())).thenReturn(userResponse);

    ResponseEntity<UserResponse> response = userController.update(1L, request, null);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void delete_ShouldReturnNoContent() {
    ResponseEntity<Void> response = userController.delete(1L);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(userService, times(1)).deleteById(1L);
  }
}
