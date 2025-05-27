package com.autenticado.autenticado.service;

import org.springframework.stereotype.Service;

import com.autenticado.autenticado.model.Usuario;
import com.autenticado.autenticado.webclient.UsuarioClient;

@Service
public class AuthServiceImpl implements AuthService {
     private final UsuarioClient usuarioClient;

    public AuthServiceImpl(UsuarioClient usuarioClient) {
        this.usuarioClient = usuarioClient;
    }
    @Override
    public Usuario buscarPorNickname(String nickname) {
        return usuarioClient.obtenerPorNickname(nickname);
    }

    @Override
    public Usuario autenticar(String nickname, String password) {
        Usuario usuario = usuarioClient.obtenerPorNickname(nickname);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }
}   