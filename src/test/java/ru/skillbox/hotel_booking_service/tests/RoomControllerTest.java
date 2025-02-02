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
import ru.skillbox.hotel_booking_service.service.RoomService;
import ru.skillbox.hotel_booking_service.web.controller.RoomController;
import ru.skillbox.hotel_booking_service.web.model.*;

@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

  @Mock
  private RoomService roomService;

  @InjectMocks
  private RoomController roomController;

  @Test
  void findById_ShouldReturnRoomResponse() {
    RoomResponse response = new RoomResponse();
    when(roomService.findById(1L)).thenReturn(response);

    ResponseEntity<RoomResponse> result = roomController.findById(1L);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void create_ShouldReturnCreatedResponse() {
    UpsertRoomRequest request = new UpsertRoomRequest();
    RoomResponse response = new RoomResponse();

    when(roomService.save(request, 1L)).thenReturn(response);

    ResponseEntity<RoomResponse> result = roomController.create(request, 1L);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void update_ShouldReturnUpdatedRoom() {
    UpdateRoomRequest request = new UpdateRoomRequest();
    RoomResponse response = new RoomResponse();

    when(roomService.update(1L, request)).thenReturn(response);

    ResponseEntity<RoomResponse> result = roomController.update(1L, request);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void delete_ShouldReturnNoContent() {
    doNothing().when(roomService).deleteById(1L);

    ResponseEntity<Void> result = roomController.delete(1L);

    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
  }

  @Test
  void filterBy_ShouldReturnFilteredRooms() {
    RoomFilter filter = new RoomFilter();
    RoomListResponse response = new RoomListResponse();

    when(roomService.filterBy(filter)).thenReturn(response);

    ResponseEntity<RoomListResponse> result = roomController.filterBy(filter);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
  }
}
