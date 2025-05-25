package com.estado.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estado.model.Estado;
import com.estado.service.EstadoService;

@RestController
@RequestMapping("/api/v1/estado")
public class EstadoController {
    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    // GET /api/v1/estado - Lista todos los estados
    @GetMapping
    public List<Estado> getAllEstados() {
        return estadoService.getAllEstados();
    }

    // POST /api/v1/estado - Crea un nuevo estado
    @PostMapping
    public Estado createEstado(@RequestBody Estado estado) {
        Estado created = estadoService.createEstado(estado);
        // Opcional: Notificar a otros microservicios al crear
        // estadoService.notifyOtherMicroservices(created.getIdEstado());
        return created;
    }

    // POST /api/v1/estado/notify/idEstado=1 - Notificar a otros microservicios
    @PostMapping("/notify")
    public Map<String, Object> notifyOtherMicroservices(@RequestParam Long idEstado) {
        try {
            estadoService.notifyOtherMicroservices(idEstado);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Notificación enviada con éxito a otros microservicios");
            response.put("idEstado", idEstado);
            return response;
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return error;
        }
    }
}