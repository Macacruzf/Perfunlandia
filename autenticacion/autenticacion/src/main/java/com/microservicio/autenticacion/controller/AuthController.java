package com.microservicio.autenticacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.autenticacion.model.Usuario;
import com.microservicio.autenticacion.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
     private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
        Usuario autenticado = authService.authenticate(usuario.getCorreo(), usuario.getPassword());

        if (autenticado != null) {
            return new ResponseEntity<>(autenticado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Si no se autenticó correctamente
        }
    }

}
