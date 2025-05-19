package com.example.gestionlogistica.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reabastecimientos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reabastecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long proveedorId;

    @Column(nullable = false)
    private String productos; // JSON con lista de productos y cantidades

    @Column(nullable = false)
    private String estado; // solicitado, recibido

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column
    private LocalDateTime fechaRecepcion;
}
