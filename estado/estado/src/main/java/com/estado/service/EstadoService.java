package com.estado.service;

import java.util.List;

import com.estado.dto.EstadoRespuestaDTO;
import com.estado.model.Estado;

public interface EstadoService {
    Estado guardarEstado(Estado estado);
    List<EstadoRespuestaDTO> obtenerEstadosPorVenta(Long ventaId);

}
