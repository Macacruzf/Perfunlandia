package com.autenticacion.autenticado.model;

import lombok.Data;

@Data
public class Usuario {
    private Long idUsuario;
    private String nickname;
    private String password;
    private String correo;
    private Rol rol;

}
