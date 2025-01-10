package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.services.JsonPlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
