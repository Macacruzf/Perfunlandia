package com.estado.controller;


import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.estado.dto.EstadoRespuestaDTO;
import com.estado.model.Estado;
import com.estado.service.EstadoService;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {
   private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @PostMapping
    public Estado crearEstado(@RequestBody Estado estado) {
        return estadoService.guardarEstado(estado);
    }

    @GetMapping("/venta/{ventaId}")
    public List<EstadoRespuestaDTO> obtenerEstadosPorVenta(@PathVariable Long ventaId) {
        return estadoService.obtenerEstadosPorVenta(ventaId);
    }
}