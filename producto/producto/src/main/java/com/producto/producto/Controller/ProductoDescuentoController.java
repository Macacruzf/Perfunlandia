package com.producto.producto.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.producto.producto.model.ProductoDescuento;
import com.producto.producto.service.ProductoDescuentoService;


@RestController
@RequestMapping("/api/v1/productodescuento")
public class ProductoDescuentoController {

    private final ProductoDescuentoService service;

    public ProductoDescuentoController(ProductoDescuentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductoDescuento> crearAsociacion(@RequestBody ProductoDescuento dto) {
        ProductoDescuento saved = service.guardar(dto);
        return ResponseEntity.status(201).body(saved);
    }
}