package com.promociones.model;



import jakarta.persistence.Column;
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
    private Long idDescuento;

    @Column(nullable = false)
    private Double porcenDescuento;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private Integer limite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_promocion", nullable = false)
    private Promocion promocion;
}
