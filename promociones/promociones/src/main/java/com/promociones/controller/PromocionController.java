package com.promociones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promociones.dto.CuponRequest;
import com.promociones.dto.CuponResponse;
import com.promociones.model.Cupon;
import com.promociones.service.PromocionService;

@RestController
@RequestMapping("/api/v1/promociones")
public class PromocionController {
@Autowired
    private PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<Cupon>> getAllCupones() {
        List<Cupon> cupones = promocionService.getAllCupones();
        if (cupones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cupones);
    }

    @PostMapping
    public ResponseEntity<?> createCupon(@RequestBody Cupon cupon) {
        try {
            Cupon nuevoCupon = promocionService.createCupon(cupon);
            return ResponseEntity.status(201).body(nuevoCupon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/aplicar")
    public ResponseEntity<?> applyCupon(@RequestBody CuponRequest request) {
        try {
            CuponResponse response = promocionService.applyCupon(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
