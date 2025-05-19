package com.example.gestionventas.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PagoClient {
private final WebClient webClient;

    public PagoClient(@Value("${pago-service.url}") String pagoServiceUrl) {
        if (pagoServiceUrl == null || pagoServiceUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del servicio de pagos no puede ser nula o vacía");
        }
        this.webClient = WebClient.builder().baseUrl(pagoServiceUrl).build();
    }

    public void procesarPago(PagoController.PagoRequest request) {
        this.webClient.post()
                .uri("/api/v1/pagos/procesar")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Error al procesar pago")))
                .bodyToMono(Void.class)
                .block();
    }

    public void procesarReembolso(PagoController.ReembolsoRequest request) {
        this.webClient.post()
                .uri("/api/v1/pagos/reembolsar")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Error al procesar reembolso")))
                .bodyToMono(Void.class)
                .block();
    }
}