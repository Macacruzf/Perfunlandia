package com.promociones.dto;

import lombok.Data;

@Data
public class CuponResponse {
    private String codigo;
    private Double descuentoAplicado;
    private String mensaje;
}
