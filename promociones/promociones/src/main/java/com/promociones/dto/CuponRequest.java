package com.promociones.dto;

import lombok.Data;

//transferir datos entre diferentes app
@Data
public class CuponRequest {
    private String codigo;
    private Long pedidoId;
}
