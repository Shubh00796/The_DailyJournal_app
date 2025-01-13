package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.services.CricketDataService;
import com.sprinboot2025.demo.utility.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cricket")
public class CricketController {

    private final CricketDataService cricketDataService;

    public CricketController(CricketDataService cricketDataService) {
        this.cricketDataService = cricketDataService;
    }

    @GetMapping("/series")
    public Mono<ApiResponse> getSeriesData() {
        return cricketDataService.getSeriesData();
    }
}
