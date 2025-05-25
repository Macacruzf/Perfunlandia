package com.estado.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estado.model.EstadoEnvio;
import com.estado.service.EstadoEnvioService;

@RestController
@RequestMapping("/api/v1/estadoenvio")
public class EstadoEnvioController {
    private final EstadoEnvioService estadoEnvioService;
    public EstadoEnvioController(EstadoEnvioService estadoEnvioService) {
        this.estadoEnvioService = estadoEnvioService;
    }

    @GetMapping
    public List<EstadoEnvio> getAllEstadoEnvios() {
        return estadoEnvioService.getAllEstadoEnvios();
    }

    @PostMapping
    public EstadoEnvio createEstadoEnvio(@RequestBody EstadoEnvio estadoEnvio) {
        return estadoEnvioService.createEstadoEnvio(estadoEnvio);
    }
}
