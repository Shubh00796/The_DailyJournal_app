package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.services.FeatureDataService;
import com.sprinboot2025.demo.utility.SeriesResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FixturesController {

    private final FeatureDataService featureDataService;

    public FixturesController(FeatureDataService featureDataService) {
        this.featureDataService = featureDataService;
    }

    @GetMapping("/fixtures")
    public ResponseEntity<SeriesResult> getFixtures() {
        try {
            SeriesResult result = featureDataService.getFeaturesOfSeries();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error fetching series data: {}", e.getMessage(), e);
            SeriesResult errorResult = new SeriesResult();
            return ResponseEntity.status(500).body(errorResult);
        }
    }
}
