package com.gestion.usuario.Service;
import com.gestion.usuario.Model.Rol;
import com.gestion.usuario.Repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    
     //Crea un nuevo rol
    
    public Rol crearRol(String nombre) {
        return rolRepository.crearRol(nombre);
    }

    
     // Obtiene todos los roles.
    
    public List<Rol> obtenerTodos() {
        return rolRepository.obtenerRol();
    }

    //Busca un rol por su ID.
    
    public Rol buscarPorId(Long id) {
        return rolRepository.buscarPorId(id);
    }

    
      //Guarda un nuevo rol
     
    public Rol guardarRol(Rol rol) {
        return rolRepository.guardar(rol);
    }

    
      //Actualiza un rol existente.
     
    public Rol actualizarRol(Rol rol) {
        return rolRepository.actualizar(rol);
    }

    //Elimina un rol por su ID
     
    public void eliminarRol(Long id) {
        rolRepository.eliminar(id);
    }


}
