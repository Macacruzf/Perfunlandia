package com.estado.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class ProductoClient {
    private final WebClient webClient;

    // Constructor con @Value para configurar la URL base desde application.properties
    public ProductoClient(@Value("${producto-service.url}") String productoServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(productoServiceUrl)
                .build();
    }

    public Map<String, Object> notifyProducto(Long idEstado) {
        return webClient.post()
                .uri("/api/v1/producto/estado")  // endpoint relativo
                .bodyValue(idEstado)
                .retrieve()
                .bodyToMono(Map.class)
                .onErrorResume(e -> Mono.just(Map.of("error", e.getMessage())))
                .block();
    }
}

