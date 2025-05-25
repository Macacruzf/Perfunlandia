package com.promociones.controller;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.promociones.model.Promocion;
import com.promociones.service.PromocionService;

@RestController
@RequestMapping("/api/v1/promociones")
public class PromocionController {
    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    @PostMapping
    public ResponseEntity<Promocion> crearPromocion(@RequestBody Promocion promocion) {
        return ResponseEntity.status(201).body(promocionService.crearPromocion(promocion));
    }
}