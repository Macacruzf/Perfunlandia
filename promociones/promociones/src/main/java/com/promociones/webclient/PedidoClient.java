package com.promociones.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PedidoClient {
    private final WebClient webClient;

    public PedidoClient(@Value("${pedidos-service.url}") String pedidosServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(pedidosServiceUrl).build();
    }

    public Map<String, Object> getPedidoById(Long id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Pedido no encontrado")))
                .bodyToMono(Map.class)
                .block();
    }
}
