package com.example.gestionventas.model;


import java.math.BigDecimal;
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
@Table(name = "venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(nullable = false)
    private Long idUsuario; // Solo guarda el ID, datos de Usuario se obtienen via WebClient

    @Column(nullable = false)
    private Long idDireccion; // Solo guarda el ID, datos de Dirección se obtienen via WebClient

    @Column(nullable = false)
    private Long idEstadoEnvio; // Solo guarda el ID, datos de EstadoEnvio se obtienen via WebClient

    @Column(nullable = false)
    private LocalDateTime fechaventa;

    @Column(nullable = false)
    private BigDecimal total;

}
