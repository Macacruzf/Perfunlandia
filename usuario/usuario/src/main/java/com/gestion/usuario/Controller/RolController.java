package com.gestion.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.usuario.model.Rol;
import com.gestion.usuario.service.RolService;

@RestController
@RequestMapping("/roles") // Ruta base para las solicitudes (localhost:8080/roles)
public class RolController {
    @Autowired
    private RolService rolService;

    
     // Crea un nuevo rol.
     
    @PostMapping // Indica que esta ruta responde a solicitudes POST
    public Rol crearRol(@RequestBody String nombre) {
        return rolService.crearRol(nombre);
    }

    
     // Obtiene todos los roles.
     
    @GetMapping // Responde a solicitudes GET para obtener todos los roles
    public List<Rol> obtenerRoles() {
        return rolService.obtenerTodos();
    }

    
     // Busca un rol por su ID.
     
    @GetMapping("/{id}") // Responde a solicitudes GET con un parámetro de ID
    public Rol obtenerRolPorId(@PathVariable Long id) {
        return rolService.buscarPorId(id);
    }

    
      //Actualiza los datos de un rol.
     
    @PutMapping("/{id}") // Responde a solicitudes PUT para actualizar el rol
    public Rol actualizarRol(@PathVariable Long id, @RequestBody Rol rol) {
        rol.setIdRol(id); // Aseguramos que el ID del rol es el correcto
        return rolService.actualizarRol(rol);
    }

   
     //Elimina un rol por su ID.
     
    @DeleteMapping("/{id}") // Responde a solicitudes DELETE para eliminar un rol
    public void eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
    }

}
