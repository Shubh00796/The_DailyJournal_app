package com.sprinboot2025.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprinboot2025.demo.utility.ApiResponse;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Slf4j
@Service
public class CricketDataService {

    private static final String API_KEY = "0c67145834msh4a2b63262809f7dp19e401jsnf653d91eb538";
    private static final String API_HOST = "cricket-live-data.p.rapidapi.com";
    private static final String BASE_URL = "https://" + API_HOST;

    private final WebClient webClient;

    public CricketDataService(WebClient.Builder webClientBuilder) {
        // Create a WebClient with increased buffer size limit and a timeout
        this.webClient = webClientBuilder
                .baseUrl(BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(30))  // Set a response timeout (optional)
                                .tcpConfiguration(tcpClient ->
                                        tcpClient.option(ChannelOption.SO_RCVBUF, 16 * 1024 * 1024) // 16 MB buffer
                                )
                ))
                .build();
    }

    public Mono<ApiResponse> getSeriesData() {
        return webClient.get()
                .uri("/series")
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    String errorMessage = "Client error: " + response.statusCode();
                    log.error(errorMessage);
                    return Mono.error(new RuntimeException(errorMessage));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    String errorMessage = "Server error: " + response.statusCode();
                    log.error(errorMessage);
                    return Mono.error(new RuntimeException(errorMessage));
                })
                .bodyToMono(String.class)  // Log the raw response as a String
                .doOnNext(response -> {
                    // Log the raw response
                    log.info("Raw response: {}", response);
                })
                .flatMap(response -> {
                    if (response == null || response.isEmpty()) {
                        log.warn("Received empty response body");
                        return Mono.error(new RuntimeException("Empty response body"));
                    }
                    try {
                        // Deserialize manually to ApiResponse if needed
                        ApiResponse apiResponse = new ObjectMapper().readValue(response, ApiResponse.class);
                        return Mono.just(apiResponse);
                    } catch (Exception e) {
                        log.error("Error deserializing response: {}", e.getMessage());
                        return Mono.error(new RuntimeException("Error deserializing response"));
                    }
                })
                .doOnError(e -> log.error("Error fetching series data: {}", e.getMessage(), e))
                .onErrorResume(e -> {
                    log.warn("API returned empty data or error: {}", e.getMessage());
                    return Mono.just(new ApiResponse()); // Return an empty response as a fallback
                });
    }

}
