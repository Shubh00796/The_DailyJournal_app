package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.services.CricketDataService;
import com.sprinboot2025.demo.utility.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class CricketDataController {

    private final CricketDataService cricketDataService;

    public CricketDataController(CricketDataService cricketDataService) {
        this.cricketDataService = cricketDataService;
    }

    @GetMapping("/series")
    public ResponseEntity<ApiResponse> getSeriesData() {
        try {
            ApiResponse apiResponse = cricketDataService.getSeriesData();
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            log.error("Error fetching series data: {}", e.getMessage(), e);
            ApiResponse errorResponse = new ApiResponse();
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
