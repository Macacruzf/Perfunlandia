package com.producto.producto.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
        if (descuentoId == null) {
            return null;
        }
        try {
            Map<String, Object> descuento = webClient.get()
                    .uri("/descuentos/{id}", descuentoId)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(),
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Descuento no encontrado")))
                    .bodyToMono(Map.class)
                    .block();
            if (descuento == null || !descuento.containsKey("idDescuento")) {
                return null;
            }
            return descuento;
        } catch (RuntimeException e) {
            System.err.println("Error en PromocionClient: " + e.getMessage());
            return null;
        }
    }
}
