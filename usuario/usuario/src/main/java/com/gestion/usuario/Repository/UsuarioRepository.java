package com.gestion.usuario.Repository;

import com.gestion.usuario.Model.Usuario;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
@Repository
public class UsuarioRepository {

    private List<Usuario> listausuarios = new ArrayList<>();
    private long idCounter = 1L;

    public Usuario creaUsuario(int rut, String nombre, String correo, int contacto, String fechaRegistro ){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idCounter);
        usuario.setRut(rut);
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContacto(contacto);
        usuario.setFechaRegistro(fechaRegistro);
        listausuarios.add(usuario);
        idCounter++;
        return usuario;
    }
    public List<Usuario> obtenerUsuarios() {
        return listausuarios;
    }
    public Usuario buscarPorId(Long id) {
        for (Usuario usuario : listausuarios) {
            if (usuario.getIdUsuario().equals(id)) { // Comparación directa entre Long
                return usuario;
            }
        }
        return null;
    }
    public Usuario guardar(Usuario usuario) {
        listausuarios.add(usuario);
        return usuario;
    }
    public Usuario actualizar(Usuario usuario) {
        for (int i = 0; i < listausuarios.size(); i++) {
            if (listausuarios.get(i).getIdUsuario().equals(usuario.getIdUsuario())) {
                listausuarios.set(i, usuario);
                return usuario;
            }
        }
        return null;
    }
    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            listausuarios.remove(usuario);
        }
    }



}
