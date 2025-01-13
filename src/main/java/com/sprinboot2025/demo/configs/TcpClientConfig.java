package com.sprinboot2025.demo.configs;

import io.netty.channel.ChannelOption;
import reactor.netty.tcp.TcpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TcpClientConfig {

    @Bean
    public TcpClient tcpClient() {
        return TcpClient.create()
                .option(ChannelOption.SO_RCVBUF, 32 * 1024 * 1024)  // Increase buffer size to 32MB
                .doOnConnected(connection -> {
                    // Additional connection-level configurations if needed
                });
    }
}
