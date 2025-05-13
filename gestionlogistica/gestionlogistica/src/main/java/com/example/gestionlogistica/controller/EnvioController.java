package com.example.gestionlogistica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionlogistica.model.Envio;
import com.example.gestionlogistica.service.EnvioService;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {
    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        List<Envio> envios = envioService.listarEnvios();
        return envios.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarEnvioPorId(@PathVariable Long id) {
        Envio envio = envioService.buscarEnvioPorId(id);
        return envio != null 
            ? ResponseEntity.ok(envio) 
            : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Envio> guardarEnvio(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.guardarEnvio(envio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEnvio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarEnvio(@PathVariable Long id) {
        envioService.borrarEnvio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Envio>> buscarPorEstado(@PathVariable String estado) {
        List<Envio> envios = envioService.buscarPorEstado(estado);
        return envios.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(envios);
    }
}



