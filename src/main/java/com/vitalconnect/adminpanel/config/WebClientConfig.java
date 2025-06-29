package com.vitalconnect.adminpanel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081") // URL de user-service
                .build();
    }
    
}
