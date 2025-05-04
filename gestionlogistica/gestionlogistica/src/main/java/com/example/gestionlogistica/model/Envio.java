package com.example.gestionlogistica.model;

import java.time.LocalDate;

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
@Table(name = "envios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoPedido;

    @Column(nullable = false)
    private String direccionDestino;

    @Column(nullable = false)
    private String estado; // Ej: "En preparación", "En tránsito", "Entregado"

    @Column(nullable = false)
    private LocalDate fechaEntrega;

}
