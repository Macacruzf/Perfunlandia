package com.example.inventarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventarios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInventario") //los nombra para mayor orden
    private Long id;

    @Column(name = "idProducto", nullable = false)
    private Long idProducto;

    @ManyToOne //para que se conecte con la otra tabla
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;

    @Column(nullable = false)
    private Integer cantidad;
}
