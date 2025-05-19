package com.example.gestionventas.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InventarioClient {
    private final WebClient webClient;

    public InventarioClient(@Value("${inventario-service.url}") String inventarioServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(inventarioServiceUrl).build();
    }

    public Map<String, Object> getProductoById(Long id) {
        return this.webClient.get()
                .uri("/productos/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Producto no encontrado")))
                .bodyToMono(Map.class).block();
    }

    public void actualizarStock(Long productoId, Integer cantidad) {
        this.webClient.put()
                .uri("/productos/{id}/stock", productoId)
                .bodyValue(Map.of("cantidad", -cantidad))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Error al actualizar stock")))
                .bodyToMono(Void.class).block();
    }
}
