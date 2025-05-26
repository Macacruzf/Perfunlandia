package com.gestion.usuario.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.usuario.model.Rol;
import com.gestion.usuario.model.Usuario;
import com.gestion.usuario.repository.RolRepository;
import com.gestion.usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
     @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Detectar rol por correo 
        String correo = usuario.getCorreo().toLowerCase();

        String nombreRol;
        if (correo.contains("admin")) {
            nombreRol = "Admin";
        } else if (correo.contains("gerente")) {
            nombreRol = "Gerente";
        } else if (correo.contains("logistica")) {
            nombreRol = "Logística";
        } else {
            nombreRol = "Cliente";
        }

        Rol rol = rolRepository.findByNombreRol(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRol));

        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorNickname(String nickname) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByNickname(nickname);
        return optionalUsuario.orElse(null);
    }
    @Override
    public Usuario buscarPorId(Long idUsuario) {
    Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
    return optionalUsuario.orElse(null);
    }
}
