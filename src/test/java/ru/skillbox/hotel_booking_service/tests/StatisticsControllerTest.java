package ru.skillbox.hotel_booking_service.tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skillbox.hotel_booking_service.service.StatisticsService;
import ru.skillbox.hotel_booking_service.web.controller.StatisticsController;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class StatisticsControllerTest {

  @Mock
  private StatisticsService statisticsService;

  @InjectMocks
  private StatisticsController statisticsController;

  @Test
  void downloadStatistics_ShouldReturnCsvFile() throws IOException {
    String csvData = "id,name\n1,Test Hotel";
    when(statisticsService.exportStatisticsToCSV()).thenReturn(csvData);

    ResponseEntity<Resource> response = statisticsController.downloadStatistics();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("attachment; filename=statistics.csv", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
  }

  @Test
  void downloadStatistics_WhenIOException_ShouldReturnServerError() throws IOException {
    when(statisticsService.exportStatisticsToCSV()).thenThrow(new RuntimeException("IO error"));

    ResponseEntity<Resource> response = statisticsController.downloadStatistics();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
  }
}
