
package com.gestion.usuario.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Long idUsuario;
    private int rut;
    private String nombre;
    private String correo;
    private int contacto;
    private String fechaRegistro;

}




