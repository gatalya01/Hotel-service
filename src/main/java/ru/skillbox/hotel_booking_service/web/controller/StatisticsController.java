package ru.skillbox.hotel_booking_service.web.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.hotel_booking_service.service.StatisticsService;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadStatistics() {
        try {
            log.info("Request received to download statistics.");
            // Получаем данные в формате CSV
            String csvData = statisticsService.exportStatisticsToCSV();
            log.info("Generated CSV: {}", csvData);

            // Преобразуем строку в InputStreamResource
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(csvData.getBytes());
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

            // Настраиваем заголовки для скачивания файла
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistics.csv");

            ResponseEntity<Resource> body = ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvData.getBytes().length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

            log.info("Generated response: {}", body);
            return body;

        } catch (IOException e) {
            log.error("Error generating statistics CSV", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new InputStreamResource(new ByteArrayInputStream("Error generating CSV".getBytes())));
        }
    }
}

