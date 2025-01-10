    package com.sprinboot2025.demo.services;

    import com.fasterxml.jackson.databind.JsonNode;
    import lombok.extern.slf4j.Slf4j;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Service;
    import org.springframework.web.reactive.function.client.WebClient;
    import org.springframework.web.reactive.function.client.WebClientResponseException;
    import reactor.core.publisher.Mono;
    @Slf4j
    @Service
    public class WeatherService {

        private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

        private static final String API_KEY = "babea779fae4b4fd9793e4e377b82cb8";
        private static final String BASE_URL = "https://api.weatherstack.com/current";

        private final WebClient webClient;

        public WeatherService(WebClient webClient) {
            this.webClient = webClient.mutate().baseUrl(BASE_URL).build();
        }

        public String getWeather(String city) {
            try {
                return webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("access_key", API_KEY)
                                .queryParam("query", city)
                                .build())
                        .retrieve()
                        .onStatus(
                                status -> status.is4xxClientError(),
                                response -> Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                        )
                        .onStatus(
                                status -> status.is5xxServerError(),
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
      private JsonNode
    }
