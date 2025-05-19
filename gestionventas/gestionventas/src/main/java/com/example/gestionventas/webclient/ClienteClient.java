package com.example.gestionventas.webclient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class ClienteClient {
    private final WebClient webClient;

    public ClienteClient(@Value("${cliente-service.url}") String clienteServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(clienteServiceUrl).build();
    }

    public Map<String, Object> getClienteById(Long id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Cliente no encontrado")))
                .bodyToMono(Map.class).block();
    }
}
