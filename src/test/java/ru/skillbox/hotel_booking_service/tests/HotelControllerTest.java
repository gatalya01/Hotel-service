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
import ru.skillbox.hotel_booking_service.service.HotelService;
import ru.skillbox.hotel_booking_service.web.controller.HotelController;
import ru.skillbox.hotel_booking_service.web.model.*;

@ExtendWith(MockitoExtension.class)
class HotelControllerTest {

  @Mock
  private HotelService hotelService;

  @InjectMocks
  private HotelController hotelController;

  @Test
  void findAll_ShouldReturnHotelList() {
    HotelListResponse response = new HotelListResponse();
    when(hotelService.findAll(0, 10)).thenReturn(response);

    ResponseEntity<HotelListResponse> result = hotelController.findAll(0, 10);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void findById_ShouldReturnHotelResponse() {
    HotelResponse response = new HotelResponse();
    when(hotelService.findById(1L)).thenReturn(response);

    ResponseEntity<HotelResponse> result = hotelController.findById(1L);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void create_ShouldReturnCreatedResponse() {
    UpsertHotelRequest request = new UpsertHotelRequest();
    HotelResponse response = new HotelResponse();

    when(hotelService.save(request)).thenReturn(response);

    ResponseEntity<HotelResponse> result = hotelController.create(request);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void update_ShouldReturnUpdatedHotel() {
    UpdateHotelRequest request = new UpdateHotelRequest();
    HotelResponse response = new HotelResponse();

    when(hotelService.update(1L, request)).thenReturn(response);

    ResponseEntity<HotelResponse> result = hotelController.update(1L, request);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
  }

  @Test
  void delete_ShouldReturnNoContent() {
    doNothing().when(hotelService).deleteById(1L);

    ResponseEntity<Void> result = hotelController.delete(1L);

    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
  }

  @Test
  void addMark_ShouldReturnSuccessMessage() {
    doNothing().when(hotelService).updateHotelRating(1L, 4.5);

    ResponseEntity<String> result = hotelController.addMark(1L, 4.5);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("Mark is successfully added. Thank you!", result.getBody());
  }

  @Test
  void filterBy_ShouldReturnFilteredHotels() {
    HotelFilter filter = new HotelFilter();
    HotelListResponse response = new HotelListResponse();

    when(hotelService.filterBy(filter)).thenReturn(response);

    ResponseEntity<HotelListResponse> result = hotelController.filterBy(filter);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
  }
}
