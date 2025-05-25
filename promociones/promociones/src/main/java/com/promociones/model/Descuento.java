package com.promociones.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "descuentos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_descuento")
    private Long idDescuento;

    @Column(name = "porcen_descuento", nullable = false)
    private Double porcenDescuento;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private Integer limite;

    @ElementCollection
    @CollectionTable(
        name = "descuento_productos",
        joinColumns = @JoinColumn(name = "id_descuento")
    )
    @Column(name = "id_producto")
    private List<Long> idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_promocion", nullable = false)
    private Promocion promocion;
}
