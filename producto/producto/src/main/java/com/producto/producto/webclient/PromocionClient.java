package com.producto.producto.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Map;

/**
 * Cliente para comunicarse con el microservicio de promociones.
 * Permite consultar los detalles de un descuento.
 */

@Component
public class PromocionClient {
    private final WebClient webClient;

    public PromocionClient(@Value("${promocion-service.url}") String promocionServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(promocionServiceUrl).build();
    }

    public Map<String, Object> getDescuentoById(Long descuentoId) {
        if (descuentoId == null) return null;

        try {
            return webClient.get()
                    .uri("/descuentos/{id}", descuentoId)
                    .retrieve()
                    .bodyToMono(Map.class)  // Mono<Map> aceptado por Spring
                    .block();
        } catch (Exception e) {
            System.err.println("Error al obtener descuento: " + e.getMessage());
            return null;
        }
    }
}
