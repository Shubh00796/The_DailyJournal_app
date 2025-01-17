package com.sprinboot2025.demo.services;

import com.sprinboot2025.demo.utility.Comments;
import com.sprinboot2025.demo.utility.Post;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JsonPlaceholderService {
    //    private static final Logger logger = LoggerFactory.getLogger(JsonPlaceholderService.class);
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com"; // Correct base URL

    private final WebClient webClient;

    public JsonPlaceholderService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getPosts() {
        try {
            return webClient.get()
                    .uri(BASE_URL + "/posts") // Correct URI path
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
                    .doOnError(e -> log.error("Error occurred while fetching posts: {}", e.getMessage(), e)); // Log error
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<String> getPostById(int id) {
        try {
            return webClient.get()
                    .uri(BASE_URL + "/posts/{id}", id) // Correct URI path
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
                    .doOnError(e -> log.error("Error occurred while fetching post by ID: {}", e.getMessage(), e)); // Log error
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<String> createPost(Post post) {
        try {
            return webClient.post()
                    .uri(BASE_URL + "/posts")  // Corrected URL: "/posts"
                    .bodyValue(post)
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
                    .doOnError(e -> log.error("Error occurred while creating post: {}", e.getMessage(), e)); // Log error

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Mono<String> createComments(Comments comments){
        try {
            return  webClient.post()
                    .uri(BASE_URL + "/comments")
                    .bodyValue(comments)
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
                    .doOnError(e -> log.error("Error occurred while fetching posts: {}", e.getMessage(), e)); // Log error
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<String> getComments() {
        try {
            return webClient.get()
                    .uri(BASE_URL + "/comments")
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
                    .doOnError(e -> log.error("Error occurred while fetching posts: {}", e.getMessage(), e)); // Log error

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<String> getCommentsByID(int id) {
        try {
            return webClient.get()
                    .uri(BASE_URL + "/comments/{id}", id)
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
                    .doOnError(e -> log.error("Error occurred while fetching posts: {}", e.getMessage(), e)); // Log error
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
