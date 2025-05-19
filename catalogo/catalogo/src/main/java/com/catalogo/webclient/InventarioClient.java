package com.catalogo.webclient;

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
                                .map(body -> new RuntimeException("Producto no encontrado en inventario")))
                .bodyToMono(Map.class).block();
    }

    public void validarProducto(Long id) {
        this.webClient.get()
                .uri("/productos/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Producto no encontrado en inventario")))
                .bodyToMono(Map.class).block();
    }
}

