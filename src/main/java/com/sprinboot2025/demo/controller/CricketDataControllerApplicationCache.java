package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.services.CricketDataServiceByAppicationCache;
import com.sprinboot2025.demo.utility.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CricketDataControllerApplicationCache {

    @Autowired
    private CricketDataServiceByAppicationCache cricketDataServiceByAppicationCache;

    // Endpoint to get series data
    @GetMapping("/series")
    public ResponseEntity<ApiResponse> getSeriesData() {
        try {
            // Call the service method to fetch series data
            ApiResponse response = cricketDataServiceByAppicationCache.getSeriesData();

            // Return the response with HTTP status OK (200)
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error and return an internal server error (500) if something goes wrong
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
