package com.promociones.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.promociones.model.Descuento;
import com.promociones.service.DescuentoService;

@RestController
@RequestMapping("/api/v1/descuentos")
public class DescuentoController {
    private final DescuentoService descuentoService;

    public DescuentoController(DescuentoService descuentoService) {
        this.descuentoService = descuentoService;
    }

    @PostMapping
    public ResponseEntity<Descuento> crearDescuento(
            @RequestBody Descuento descuento, 
            @RequestParam List<Long> productosIds) {
        return ResponseEntity.status(201).body(descuentoService.crearDescuento(descuento, productosIds));
    }

    @GetMapping("/codigo")
    public ResponseEntity<Descuento> obtenerDescuentoPorCodigo(@RequestParam String codigo) {
        return ResponseEntity.ok(descuentoService.obtenerDescuentoPorCodigo(codigo));
    }

    @GetMapping("/activo")
    public ResponseEntity<List<Descuento>> obtenerDescuentosActivosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaActual) {
        List<Descuento> descuentos = descuentoService.obtenerDescuentosActivosPorFecha(fechaActual);
        return descuentos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(descuentos);
    }
}