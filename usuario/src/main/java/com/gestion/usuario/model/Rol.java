package com.gestion.usuario.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Long idRol;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombreRol;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("rol")
    private List<Usuario> usuarios;

}
