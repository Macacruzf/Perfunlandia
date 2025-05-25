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
import com.promociones.repository.DescuentoRepository;
import com.promociones.service.DescuentoService;

@RestController
@RequestMapping("/api/v1/descuentos")
public class DescuentoController {
    private final DescuentoService descuentoService;
    private final DescuentoRepository descuentoRepository;

    public DescuentoController(DescuentoService descuentoService, DescuentoRepository descuentoRepository) {
        this.descuentoService = descuentoService;
        this.descuentoRepository = descuentoRepository;
    }

    @PostMapping
    public ResponseEntity<Descuento> crearDescuento(
        @RequestBody Descuento descuento,
        @RequestParam List<Long> productosIds
    ) {
        return ResponseEntity.status(201).body(descuentoService.crearDescuento(descuento, productosIds));
    }

    @GetMapping("/activo")
    public ResponseEntity<Long> getDescuentoActivo(
        @RequestParam Long productoId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaActual
    ) {
        List<Descuento> descuentos = descuentoRepository.findDescuentosActivosPorProducto(productoId, fechaActual);
        return descuentos.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(descuentos.get(0).getIdDescuento());
    }
}

