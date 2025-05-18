package com.example.resenas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.resenas.model.Resena;
import com.example.resenas.service.ResenaService;

@RestController
@RequestMapping

public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody Resena resena) {
        return ResponseEntity.status(201).body(resenaService.crearResena(resena));
    }

    @GetMapping("/producto/{productoId}")
    public List<Resena> obtenerPorProducto(@PathVariable Long productoId) {
        return resenaService.obtenerPorProducto(productoId);
    }

    @GetMapping("/destacadas")
    public List<Resena> obtenerDestacadas(
            @RequestParam(defaultValue = "4") Integer calificacionMinima) {
        return resenaService.obtenerDestacadas(calificacionMinima);
    }

    @GetMapping("/promedio/{productoId}")
    public ResponseEntity<Double> calcularPromedio(@PathVariable Long productoId) {
        return ResponseEntity.ok(resenaService.calcularPromedio(productoId));
    }
}