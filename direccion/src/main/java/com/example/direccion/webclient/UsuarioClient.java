package com.example.direccion.webclient;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuario-service.url}") String usuarioServiceUrl) {
        this.webClient = WebClient.builder()
                                  .baseUrl(usuarioServiceUrl)
                                  .build();
    }

    // Método para obtener información del usuario
   
    public Mono<Map> obtenerUsuario(Long idUsuario) {
        return webClient.get()
                        .uri("/api/v1/usuarios/{idUsuario}", idUsuario)
                        .retrieve()
                        .bodyToMono(Map.class);
    }

    // Método para crear o actualizar un usuario si es necesario
    public void actualizarUsuario(Long idUsuario, Map<String, Object> datosActualizados) {
        webClient.put()
                 .uri("/api/v1/usuarios/{idUsuario}", idUsuario)
                 .bodyValue(datosActualizados)
                 .retrieve()
                 .bodyToMono(Void.class)
                 .block();
    }
}