package com.estado.webclient;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.estado.dto.ProductoDTO;

import reactor.core.publisher.Mono;

@Component
public class ProductoClient {
  private final WebClient webClient;

    public ProductoClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8091/api/productos").build();
    }

    public Mono<ProductoDTO> obtenerProductoPorId(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(ProductoDTO.class);
    }
}

