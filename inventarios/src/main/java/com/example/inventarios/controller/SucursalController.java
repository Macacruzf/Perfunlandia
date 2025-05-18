package com.example.inventarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.inventarios.model.Sucursal;
import com.example.inventarios.service.SucursalService;

public class SucursalController {

@Autowired
    private SucursalService sucursalService;

    
    @GetMapping
    public ResponseEntity<List<Sucursal>> obtenerSucursales() {
        List<Sucursal> lista = sucursalService.listarSucursales();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(lista); // 200
    }

    
    @GetMapping("/{id}") //busca la sucursal por su id
    public ResponseEntity<Sucursal> obtenerSucursalPorId(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.buscarSucursalPorId(id).orElseThrow();
            return ResponseEntity.ok(sucursal); // 200
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    // Crea la nueva sucursal
    @PostMapping
    public ResponseEntity<Object> guardarSucursal(@RequestBody Sucursal nueva) {
        return ResponseEntity.status(201).body(sucursalService.guardarSucursal(nueva)); // 201
    }
}