package com.promociones.model;

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
@Table(name = "descuentos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDescuento;

    @Column(nullable = false)
    private double porcdesc;

    @Column(nullable = false)
    private Long idCupon;

    @Column(nullable = false)
    private Integer limiteUso;



}
