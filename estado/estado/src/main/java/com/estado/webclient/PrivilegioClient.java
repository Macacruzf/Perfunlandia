package com.estado.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class PrivilegioClient {
    private final WebClient webClient;

    public PrivilegioClient(@Value("${privilegio-service.url}") String privilegioServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(privilegioServiceUrl)
                .build();
    }

    public Map<String, Object> notifyPrivilegio(Long idEstado) {
        return webClient.post()
                .uri("/api/v1/privilegio/estado")
                .bodyValue(idEstado)
                .retrieve()
                .bodyToMono(Map.class)
                .onErrorResume(e -> Mono.just(Map.of("error", e.getMessage())))
                .block();
    }
}
