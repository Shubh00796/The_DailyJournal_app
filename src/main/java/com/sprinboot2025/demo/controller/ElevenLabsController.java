package com.sprinboot2025.demo.controller;

import com.sprinboot2025.demo.services.ElevenLabsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/elevenlabs")
public class ElevenLabsController {

    @Autowired
    private ElevenLabsService elevenLabsService;

    @GetMapping("/voices")
    public Mono<String> getAvailableVoices() {
        return elevenLabsService.getVoices();
    }

    @PostMapping("/text-to-speech/{voiceId}")
    public Mono<String> generateSpeech(@PathVariable String voiceId, @RequestBody Map<String, String> request) {
        return elevenLabsService.textToSpeech(voiceId, request.get("text"));
    }
}
