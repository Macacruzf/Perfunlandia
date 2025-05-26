package com.promociones.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductoClient {
    private final WebClient webClient;

    public ProductoClient(@Value("${producto-service.url}") String productoServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(productoServiceUrl).build();
    }

    public void asociarDescuentoAProducto(Long productoId, Long idDescuento) {
        webClient.post()
                 .uri("/api/v1/productodescuento")  // <-- ESTE ES EL CAMBIO CORRECTO
                 .bodyValue(Map.of("producto", Map.of("idProducto", productoId), "idDescuento", idDescuento))
                 .retrieve()
                 .toBodilessEntity()
                 .block();
    }
}
