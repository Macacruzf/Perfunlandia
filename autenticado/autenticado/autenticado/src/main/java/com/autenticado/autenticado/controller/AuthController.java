package com.autenticado.autenticado.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autenticado.autenticado.model.Usuario;
import com.autenticado.autenticado.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/usuario/{nickname}")
    public ResponseEntity<Usuario> obtenerUsuarioPorNickname(@PathVariable String nickname) {
        Usuario usuario = authService.buscarPorNickname(nickname);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String nickname, @RequestParam String password) {
        Usuario usuario = authService.autenticar(nickname, password);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.status(401).build();
    }
}
