package com.microservicio.autenticacion.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.microservicio.autenticacion.model.Usuario;

@Repository
public class UsuarioRepository {

     private List<Usuario> usuarios = new ArrayList<>();

    // Agregar algunos usuarios para probar
    public UsuarioRepository() {
        usuarios.add(new Usuario(1L, "juan@mail.com", "1234"));
        usuarios.add(new Usuario(2L, "ana@mail.com", "abcd"));
    }

    public Usuario findByCorreo(String correo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                return usuario;
            }
        }
        return null;
    }
}
