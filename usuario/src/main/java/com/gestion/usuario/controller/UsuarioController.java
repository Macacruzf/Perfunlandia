package com.gestion.usuario.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.gestion.usuario.model.Usuario;

import com.gestion.usuario.service.UsuarioService;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") // Permite llamadas desde otros servicios (como autenticación)
public class UsuarioController {
    
   @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<Usuario> obtenerPorNickname(@PathVariable String nickname){
        Usuario usuario = usuarioService.buscarPorNickname(nickname);
        if(usuario != null){
            return ResponseEntity.ok(usuario);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

   @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }
    @GetMapping("/id/{idUsuario}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long idUsuario) {
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        if (usuario != null) {
             return ResponseEntity.ok(usuario);
        }else{
             return ResponseEntity.notFound().build();
        }
    }
    

}
