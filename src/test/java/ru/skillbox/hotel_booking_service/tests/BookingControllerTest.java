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
import ru.skillbox.hotel_booking_service.service.BookingService;
import ru.skillbox.hotel_booking_service.web.controller.BookingController;
import ru.skillbox.hotel_booking_service.web.model.BookingListResponse;
import ru.skillbox.hotel_booking_service.web.model.BookingResponse;
import ru.skillbox.hotel_booking_service.web.model.UpsertBookingRequest;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

  @Mock
  private BookingService bookingService;

  @InjectMocks
  private BookingController bookingController;

  @Test
  void bookRoom_ShouldReturnCreatedResponse() {
    UpsertBookingRequest request = new UpsertBookingRequest();
    BookingResponse response = new BookingResponse();

    when(bookingService.bookRoom(request)).thenReturn(response);

    ResponseEntity<BookingResponse> result = bookingController.bookRoom(request);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void findAll_ShouldReturnBookingList() {
    BookingListResponse response = new BookingListResponse();
    when(bookingService.findAll(0, 10)).thenReturn(response);

    ResponseEntity<BookingListResponse> result = bookingController.findAll(0, 10);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void delete_ShouldReturnNoContent() {
    doNothing().when(bookingService).deleteById(1L);

    ResponseEntity<Void> result = bookingController.delete(1L);

    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
  }
}
