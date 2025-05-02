package com.microservicio.autenticacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.autenticacion.model.Usuario;
import com.microservicio.autenticacion.repository.UsuarioRepository;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario authenticate(String correo, String password) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null; // Si no se encuentra o las contraseñas no coinciden
    }

}
