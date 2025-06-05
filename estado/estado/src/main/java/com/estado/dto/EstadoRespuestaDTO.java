package com.estado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoRespuestaDTO {
    private Long ventaId;
    private String estado;
    private String descripcion;
    private String fechaEstado;
    private String nombreProducto;

}
