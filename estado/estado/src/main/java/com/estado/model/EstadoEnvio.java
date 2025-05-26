package com.estado.model;

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
@Table(name = "estado_envio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_envio")
    private Long idEstadoEnvio;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "id_venta")
    private Long idVenta;

    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = true)
    private Estado estado;
}
