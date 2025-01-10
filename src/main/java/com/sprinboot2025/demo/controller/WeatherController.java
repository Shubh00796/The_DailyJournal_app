package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @Autowired

    private  WeatherService weatherService;

//    public WeatherController(WeatherService weatherService) {
//        this.weatherService = weatherService;
//    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }
}
