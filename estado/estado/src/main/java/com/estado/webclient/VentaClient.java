package com.estado.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class VentaClient {
    private final WebClient webClient;

    public VentaClient(@Value("${venta-service.url}") String ventaServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(ventaServiceUrl)
                .build();
    }

    public Map<String, Object> notifyVenta(Long idEstado) {
        return webClient.post()
                .uri("/api/v1/venta/estadoenvio")
                .bodyValue(idEstado)
                .retrieve()
                .bodyToMono(Map.class)
                .onErrorResume(e -> Mono.just(Map.of("error", e.getMessage())))
                .block();
    }
}
