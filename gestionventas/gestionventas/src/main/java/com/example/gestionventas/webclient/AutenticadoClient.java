package com.example.gestionventas.webclient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class AutenticadoClient {
    private final WebClient webClient;

    public AutenticadoClient(@Value("${auth-service.url}") String authServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(authServiceUrl).build();
    }

    public Map<String, Object> getUsuarioById(Long id) {
        return this.webClient.get()
                .uri("/autenticado/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Autenticacion no encontrado")))
                .bodyToMono(Map.class).block();
    }
}
