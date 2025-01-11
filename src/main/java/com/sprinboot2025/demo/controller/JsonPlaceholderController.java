package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.services.JsonPlaceholderService;
import com.sprinboot2025.demo.utility.Comments;
import com.sprinboot2025.demo.utility.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/json-placeholder")
public class JsonPlaceholderController {

    private final JsonPlaceholderService jsonPlaceholderService;

    @Autowired
    public JsonPlaceholderController(JsonPlaceholderService jsonPlaceholderService) {
        this.jsonPlaceholderService = jsonPlaceholderService;
    }

    @GetMapping("/posts")
    public Mono<String> getPosts() {
        return jsonPlaceholderService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public Mono<String> getPostById(@PathVariable int id) {
        return jsonPlaceholderService.getPostById(id);
    }

    @PostMapping("/create-post")
    public Mono<ResponseEntity<String>> createPost(@RequestBody Post post) {
        return jsonPlaceholderService.createPost(post)
                .map(response -> ResponseEntity.ok("Post created successfully: " + response))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error creating post: " + e.getMessage())));
    }

    @PostMapping("/create-comments")
        public Mono<ResponseEntity<String>> createComments(@RequestBody Comments comments){
        return jsonPlaceholderService.createComments(comments)
                .map(response -> ResponseEntity.ok("Post created successfully: " + response))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error creating post: " + e.getMessage())));
        }

    @GetMapping("/comments")
    public Mono<String> getComments() {
        return jsonPlaceholderService.getComments();
    }

    @GetMapping("/comments/{id}")
    public Mono<String> getCommentsById(@PathVariable int id) {
        return jsonPlaceholderService.getCommentsByID(id);
    }
}
