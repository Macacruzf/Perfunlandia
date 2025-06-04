package com.estado.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // ✅ GET /api/v1/estado/{id} - Obtiene un estado por su ID
    @GetMapping("/{id}")
    public Estado getEstadoById(@PathVariable Long id) {
        return estadoService.getEstadoById(id)
                .orElseThrow(() -> new RuntimeException("Estado con ID " + id + " no existe"));
    }

    // POST /api/v1/estado - Crea un nuevo estado
    @PostMapping
    public Estado createEstado(@RequestBody Estado estado) {
        Estado created = estadoService.createEstado(estado);
        // Opcional: Notificar a otros microservicios al crear
        // estadoService.notifyOtherMicroservices(created.getIdEstado());
        return created;
    }

    // POST /api/v1/estado/notify?idEstado=1 - Notificar a otros microservicios
   @PostMapping("/notify")
    public Map<String, Object> notifyUser(@RequestParam Long idEstado) {
    Map<String, Object> response = new HashMap<>();
    try {
        String mensaje = estadoService.notifyUser(idEstado); // devuelve el mensaje correspondiente
        response.put("message", mensaje);
        response.put("idEstado", idEstado);
    } catch (Exception e) {
        response.put("error", e.getMessage());
    }
    return response;
}
}