package com.gestion.usuario.Repository;

import org.springframework.stereotype.Repository;

import com.gestion.usuario.Model.Rol;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RolRepository {
    private List <Rol> listaroles = new ArrayList<>();
    // Contador para asignar IDs únicos de forma automática
    private Long idCounter = 1L;

    //Crea un nuevo rol con un nombre dado, le asigna un ID único y lo guarda en la lista.

    public Rol crearRol (String nombre){
        Rol rol = new Rol(idCounter++,nombre); // Creamos el rol con ID autoincremental
        listaroles.add(rol); // Agregamos el rol a la lista
        return rol; // Devolvemos el rol creado
    }
    //Obtiene todos los roles almacenados en la lista.
    public List<Rol> obtenerRol(){
        return listaroles;
    }
    // Busca un rol por su ID en la lista.
    public Rol buscarPorId(Long id){
        for(Rol rol : listaroles){
            if(rol.getIdRol().equals(id)){ // Comparamos IDs correctamente con equals
                return rol;
            }
        }
        return null; //// Si no lo encontramos, devolvemos null
    }
    public Rol guardar(Rol rl) {
          listaroles.add(rl); // Agregamos directamente el rol recibido
          return rl;
    }
    //Actualiza un rol existente en la lista basado en su ID.
    public Rol actualizar (Rol rl){
        for (int i = 0; i < listaroles.size(); i++) {
            if (listaroles.get(i).getIdRol().equals(rl.getIdRol())) {
                listaroles.set(i, rl); // Reemplazamos el rol existente por el nuevo
                return rl;
            }
        }
        return null; // Si no encontramos el rol, devolvemos null
    
        
    }
    // Elimina un rol de la lista basado en su ID.
    public void eliminar(Long id){
        Rol rol = buscarPorId(id);
        if (rol != null){
            listaroles.remove(rol); // Si existe, lo eliminamos de la lista
        }
    }

   




}
