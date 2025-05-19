package com.example.gestionlogistica.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionlogistica.model.Envio;
import com.example.gestionlogistica.model.Proveedor;
import com.example.gestionlogistica.model.Reabastecimiento;
import com.example.gestionlogistica.model.Ruta;
import com.example.gestionlogistica.service.LogisticaService;


@RestController
@RequestMapping("/api/v1/logistica")
public class LogisticaController {
    @Autowired
    private LogisticaService logisticaService;

    // Endpoints para envíos
    @PostMapping("/envios")
    public ResponseEntity<?> crearEnvio(@RequestBody Envio envio) {
        try {
            Envio nuevoEnvio = logisticaService.crearEnvio(envio);
            return ResponseEntity.status(201).body(nuevoEnvio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/envios/{id}/estado")
    public ResponseEntity<?> actualizarEstadoEnvio(@PathVariable Long id, @RequestBody Map<String, String> estado) {
        try {
            Envio actualizado = logisticaService.actualizarEstadoEnvio(id, estado.get("estado"));
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/envios/pedido/{pedidoId}")
    public ResponseEntity<List<Envio>> obtenerEnviosPorPedido(@PathVariable Long pedidoId) {
        List<Envio> envios = logisticaService.getEnviosPorPedido(pedidoId);
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }

    // Endpoints para rutas
    @PostMapping("/rutas")
    public ResponseEntity<?> crearRuta(@RequestBody Ruta ruta) {
        try {
            Ruta nuevaRuta = logisticaService.crearRuta(ruta);
            return ResponseEntity.status(201).body(nuevaRuta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/rutas/{id}/optimizar")
    public ResponseEntity<?> optimizarRuta(@PathVariable Long id) {
        try {
            Ruta optimizada = logisticaService.optimizarRuta(id);
            return ResponseEntity.ok(optimizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Endpoints para proveedores
    @PostMapping("/proveedores")
    public ResponseEntity<?> agregarProveedor(@RequestBody Proveedor proveedor) {
        try {
            Proveedor nuevoProveedor = logisticaService.agregarProveedor(proveedor);
            return ResponseEntity.status(201).body(nuevoProveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/proveedores/{id}")
    public ResponseEntity<?> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        try {
            Proveedor actualizado = logisticaService.actualizarProveedor(id, proveedor);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/proveedores/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        try {
            logisticaService.eliminarProveedor(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Endpoints para reabastecimientos
    @PostMapping("/reabastecimientos")
    public ResponseEntity<?> crearReabastecimiento(@RequestBody Reabastecimiento reabastecimiento) {
        try {
            Reabastecimiento nuevo = logisticaService.crearReabastecimiento(reabastecimiento);
            return ResponseEntity.status(201).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/reabastecimientos/{id}/recibir")
    public ResponseEntity<?> recibirReabastecimiento(@PathVariable Long id) {
        try {
            Reabastecimiento recibido = logisticaService.recibirReabastecimiento(id);
            return ResponseEntity.ok(recibido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}



