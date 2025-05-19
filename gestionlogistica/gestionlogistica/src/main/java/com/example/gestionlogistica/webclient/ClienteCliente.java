package com.example.gestionlogistica.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class ClienteCliente {
private final WebClient webClient;

    public ClienteCliente(@Value("${cliente-service.url}") String clienteServiceUrl) {
        if (clienteServiceUrl == null || clienteServiceUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del servicio de clientes no puede ser nula o vacía");
        }
        this.webClient = WebClient.builder().baseUrl(clienteServiceUrl).build();
    }

    public Map<String, Object> getPedidoById(Long id) {
        return this.webClient.get()
                .uri("/pedidos/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Pedido no encontrado")))
                .bodyToMono(Map.class).block();
    }
}