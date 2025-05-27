package com.autenticado.autenticado.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    private Long idUsuario;
    private String nickname;
    private String password;
    private String correo;
    private Rol rol;


}
