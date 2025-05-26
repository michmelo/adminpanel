package com.vitalconnect.adminpanel.service.external;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vitalconnect.adminpanel.dto.UserDTO;

@Service
public class UserClientService {
    
    private final WebClient webClient;

    public UserClientService(WebClient userWebClient) {
        this.webClient = userWebClient;
    }

    // Reactivo
    /*
    public Mono<UserDTO> getUserById(Long userId) {
        return webClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }
    */

    // Bloqueante
    public UserDTO getUserByIdBlocking(int id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block(); // Solo usar fuera de entornos reactivos
    }
}
