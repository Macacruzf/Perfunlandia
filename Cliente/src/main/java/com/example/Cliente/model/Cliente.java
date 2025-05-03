package com.example.Cliente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {

    private int telefonoCliente;
    private String runCliente;
    private String nombresCliente;
    private String apellidosCliente;
    
    private String correoCliente;
    private int id

}
