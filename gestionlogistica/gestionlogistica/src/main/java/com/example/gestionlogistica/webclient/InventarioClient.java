package com.example.gestionlogistica.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InventarioClient {
private final WebClient webClient;

    public InventarioClient(@Value("${inventario-service.url}") String inventarioServiceUrl) {
        if (inventarioServiceUrl == null || inventarioServiceUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del servicio de inventario no puede ser nula o vacía");
        }
        this.webClient = WebClient.builder().baseUrl(inventarioServiceUrl).build();
    }

    public void actualizarStock(Long productoId, Integer cantidad) {
        this.webClient.put()
                .uri("/productos/{id}/stock", productoId)
                .bodyValue(Map.of("cantidad", cantidad))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Error al actualizar stock")))
                .bodyToMono(Void.class).block();
    }
}