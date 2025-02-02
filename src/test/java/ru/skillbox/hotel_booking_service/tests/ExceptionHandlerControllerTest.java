package ru.skillbox.hotel_booking_service.tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import ru.skillbox.hotel_booking_service.exception.EntityNotFoundException;
import ru.skillbox.hotel_booking_service.web.controller.ExceptionHandlerController;
import ru.skillbox.hotel_booking_service.web.model.ErrorResponse;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerControllerTest {

  @InjectMocks
  private ExceptionHandlerController exceptionHandlerController;

  @Test
  void notFound_ShouldReturnNotFoundResponse() {
    EntityNotFoundException ex = new EntityNotFoundException("Entity not found");
    ResponseEntity<ErrorResponse> response = exceptionHandlerController.notFound(ex);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Entity not found", response.getBody().getErrorMessage());
  }

  @Test
  void notValid_ShouldReturnBadRequestResponse() {
    BindingResult bindingResult = mock(BindingResult.class);
    when(bindingResult.getAllErrors()).thenReturn(Collections.emptyList());
    MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

    ResponseEntity<ErrorResponse> response = exceptionHandlerController.notValid(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void handleMissingParams_ShouldReturnBadRequestResponse() {
    MissingServletRequestParameterException ex = new MissingServletRequestParameterException("param", "String");
    ResponseEntity<ErrorResponse> response = exceptionHandlerController.handleMissingParams(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().getErrorMessage().contains("param"));
  }

  @Test
  void handleGenericException_ShouldReturnInternalServerError() {
    Exception ex = new Exception("Unexpected error");
    ResponseEntity<ErrorResponse> response = exceptionHandlerController.handleGenericException(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Unexpected error", response.getBody().getErrorMessage());
  }
}
