package com.gestion.usuario.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.usuario.model.Usuario;
import com.gestion.usuario.service.UsuarioService;

import java.util.List;
@RestController
@RequestMapping("/usuarios")// Ruta base para las solicitudes (localhost:8080/usuarios)
public class UsuarioController {
    @Autowired
     private UsuarioService usuarioService;

    
     //Crea un nuevo usuario.
     
    @PostMapping // Indica que esta ruta responde a solicitudes POST
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    
     //Obtiene todos los usuarios.
     
     
    @GetMapping // Responde a solicitudes GET para obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerTodos();
    }

    
      //Busca un usuario por su ID.
    
    @GetMapping("/{id}") // Responde a solicitudes GET con un parámetro de ID
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    
      //Actualiza los datos de un usuario.
     
    
    @PutMapping("/{id}") // Responde a solicitudes PUT para actualizar el usuario
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setIdUsuario(id); // Aseguramos que el ID del usuario es el correcto
        return usuarioService.actualizarUsuario(usuario);
    }

    
      //Elimina un usuario por su ID.
     
    
    @DeleteMapping("/{id}") // Responde a solicitudes DELETE para eliminar un usuario
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

}
