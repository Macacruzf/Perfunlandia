package com.example.inventarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sucursales")
@Data

public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Long id;

    @Column(name = "nombre_sucursal", nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 120)
    private String direccion;

    @Column(nullable = false, length = 50)
    private String comuna;

    @Column(nullable = false, length = 50)
    private String region;
}