package com.microservicio.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor // 
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @Column(name = "id_producto", nullable = false)
    private Long idProducto; 

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal; // Asegúrate de que la clase Sucursal exista

    @Column(nullable = false)
    private Integer cantidad;
}