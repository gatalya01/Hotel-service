package ru.skillbox.hotel_booking_service.tests;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.skillbox.hotel_booking_service.service.BookingService;
import ru.skillbox.hotel_booking_service.web.controller.BookingController;
import ru.skillbox.hotel_booking_service.web.model.BookingListResponse;
import ru.skillbox.hotel_booking_service.web.model.BookingResponse;
import ru.skillbox.hotel_booking_service.web.model.UpsertBookingRequest;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private BookingService bookingService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void bookRoom_ShouldReturnCreatedResponse() throws Exception {
    UpsertBookingRequest request = new UpsertBookingRequest();
    BookingResponse response = new BookingResponse();

    given(bookingService.bookRoom(request)).willReturn(response);

    mvc.perform(post("/api/bookings")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").exists());
  }

  @Test
  void findAll_ShouldReturnBookingList() throws Exception {
    BookingListResponse response = new BookingListResponse();

    given(bookingService.findAll(0, 10)).willReturn(response);

    mvc.perform(get("/api/bookings")
        .param("page", "0")
        .param("size", "10")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.bookings").exists());
  }

  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    willDoNothing().given(bookingService).deleteById(1L);

    mvc.perform(delete("/api/bookings/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());
  }
}
