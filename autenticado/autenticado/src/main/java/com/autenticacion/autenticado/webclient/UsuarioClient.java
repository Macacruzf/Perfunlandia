
package com.autenticacion.autenticado.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.autenticacion.autenticado.model.Usuario;

@Component
public class UsuarioClient {
    private final WebClient webClient;

    public UsuarioClient(@Value("${gestionusuarios.url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Usuario obtenerPorNickname(String nickname) {
        return webClient.get()
                .uri("/usuarios/nickname/{nickname}", nickname)
                .retrieve()
                .bodyToMono(Usuario.class)
                .block(); // Síncrono para simplicidad
    }

}
