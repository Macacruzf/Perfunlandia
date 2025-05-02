package com.gestion.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.usuario.model.Usuario;
import com.gestion.usuario.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

     
      //Crea un nuevo usuario.
    
    public Usuario crearUsuario(Usuario usuario) {
        
        return usuarioRepository.guardar(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.obtenerUsuarios();
    }

    
      //Busca un usuario por su ID.
    
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.buscarPorId(id);
    }

    
     // Actualiza un usuario existente.
     
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.actualizar(usuario);
    }

    
     //Elimina un usuario por su ID.
     
    public void eliminarUsuario(Long id) {
        usuarioRepository.eliminar(id);
    }
    

}
