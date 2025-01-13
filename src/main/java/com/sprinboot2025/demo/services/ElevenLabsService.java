package com.sprinboot2025.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class ElevenLabsService {

    private final WebClient webClient;
    private final String baseUrl;
    private final String apiKey;

    // Constructor with dependency injection and property initialization
    public ElevenLabsService(WebClient.Builder webClientBuilder,
                             @Value("${elevenlabs.api.base-url}") String baseUrl,
                             @Value("${elevenlabs.api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    // Fetch voices from the ElevenLabs API
    public Mono<String> getVoices() {
        return webClient.get()
                .uri("/voices")
                .header("xi-api-key", apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Log and handle API errors
                    System.err.println("Error: " + ex.getMessage());
                    return Mono.error(new RuntimeException("Failed to fetch voices"));
                });
    }

    // Convert text to speech using the ElevenLabs API
    public Mono<String> textToSpeech(String voiceId, String text) {
        String url = "/text-to-speech/" + voiceId;

        return webClient.post()
                .uri(url)
                .header("xi-api-key", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue("{\"text\": \"" + text + "\"}")
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
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Log and handle API errors
                    System.err.println("Error: " + ex.getMessage());
                    return Mono.error(new RuntimeException("Failed to generate speech"));
                });
    }
}
