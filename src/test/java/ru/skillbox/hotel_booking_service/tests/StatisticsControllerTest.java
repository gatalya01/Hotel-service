package ru.skillbox.hotel_booking_service.tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.hotel_booking_service.service.StatisticsService;
import ru.skillbox.hotel_booking_service.web.controller.StatisticsController;

import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StatisticsService statisticsService;

  @Test
  void downloadStatistics_ShouldReturnCsvFile() throws Exception {
    String csvData = "id,name\n1,Test Hotel";
    given(statisticsService.exportStatisticsToCSV()).willReturn(csvData);

    mockMvc.perform(get("/api/v1/statistics/download"))
      .andExpect(status().isOk())
      .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistics.csv"))
      .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
      .andExpect(content().string(csvData));
  }

  @Test
  void downloadStatistics_WhenIOException_ShouldReturnServerError() throws Exception {
    given(statisticsService.exportStatisticsToCSV()).willThrow(new RuntimeException("IO error"));

    mockMvc.perform(get("/api/v1/statistics/download"))
      .andExpect(status().isInternalServerError());
  }
}
