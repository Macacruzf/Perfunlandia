package com.example.Cliente.model;

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
@Table(name="cliente")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(unique = true, length = 12, nullable = false) //Limites de los campos
    private String runCliente;

    @Column(nullable = false, length = 50)
    private String nombresCliente;

    @Column(nullable = false, length = 50)
    private String apellidosCliente;
    
    @Column(nullable = false, length = 15)
    private String telefonoCliente; //lo coloco en String por si llega a colocar con el +569

    @Column(nullable = false)
    private String correoCliente;

    @Column(nullable = false, length = 120)
    private String direccionCliente;

    @Column(nullable = false, length = 120)
    private String comunaCliente;

    @Column(nullable = false, length = 120)
    private String regionCliente;

    @Column(nullable = false, length = 10)
    private String codigoPostalCliente;
    
}
