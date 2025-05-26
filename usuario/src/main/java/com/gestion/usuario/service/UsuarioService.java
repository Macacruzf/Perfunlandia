package com.gestion.usuario.service;

import com.gestion.usuario.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Usuario buscarPorNickname(String nickname);
    Usuario buscarPorId(Long idUsuario);
    

}
