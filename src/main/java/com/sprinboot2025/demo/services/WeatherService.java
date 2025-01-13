package com.sprinboot2025.demo.services;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public String getWeather(String city) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/current") // Only the path segment
                            .queryParam("access_key", apiKey)
                            .queryParam("query", city)
                            .build())
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            response -> Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                    )
                    .onStatus(
                            HttpStatusCode::is5xxServerError,
                            response -> Mono.error(new RuntimeException("Server error: " + response.statusCode()))
                    )
                    .bodyToMono(String.class)
                    .block(); // Blocking call for simplicity
        } catch (WebClientResponseException e) {
            log.error("HTTP Error while fetching weather data for city {}: {} - {}", city, e.getStatusCode(), e.getResponseBodyAsString(), e);
            return "HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            log.error("Unexpected error while fetching weather data for city {}: {}", city, e.getMessage(), e);
            return "Error fetching weather data: " + e.getMessage();
        }
    }
}
