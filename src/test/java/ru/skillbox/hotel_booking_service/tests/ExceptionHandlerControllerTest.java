package ru.skillbox.hotel_booking_service.tests;

import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.hotel_booking_service.exception.EntityNotFoundException;
import ru.skillbox.hotel_booking_service.web.controller.BookingController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookingController.class)
class ExceptionHandlerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookingController bookingController;

  @Test
  void notFound_ShouldReturnNotFoundResponse() throws Exception {
    given(bookingController.findAll(0,10)).willThrow(new EntityNotFoundException("Entity not found"));

    mockMvc.perform(get("/api/v1/booking/1"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.errorMessage").value("Entity not found"))
      .andExpect(jsonPath("$.status").value(404))
      .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  void notValid_ShouldReturnBadRequestResponse() throws Exception {
    String invalidRequest = "{}";

    mockMvc.perform(post("/api/v1/booking")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidRequest))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errorMessage", not(emptyString())))
      .andExpect(jsonPath("$.status").value(400))
      .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  void handleMissingParams_ShouldReturnBadRequestResponse() throws Exception {
    mockMvc.perform(get("/api/v1/booking"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errorMessage", containsString("Required request parameter")))
      .andExpect(jsonPath("$.status").value(400))
      .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  void handleGenericException_ShouldReturnInternalServerError() throws Exception {
    given(bookingController.findAll(0, 10)).willThrow(new RuntimeException("Unexpected error"));

    mockMvc.perform(get("/api/v1/booking/1"))
      .andExpect(status().isInternalServerError())
      .andExpect(jsonPath("$.errorMessage").value("Unexpected error"))
      .andExpect(jsonPath("$.status").value(500))
      .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }
}
