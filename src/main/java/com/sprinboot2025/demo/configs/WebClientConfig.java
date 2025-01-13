package com.sprinboot2025.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebClientConfig {
    private final TcpClient tcpClient;

    // Inject TcpClient from TcpClientConfig
    public WebClientConfig(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.from(tcpClient);  // Use TcpClient for HTTP client configuration
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))  // Connect WebClient to HttpClient
                .build();
    }
}
