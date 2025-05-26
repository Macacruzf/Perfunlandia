package com.producto.producto.service;

import org.springframework.stereotype.Service;

import com.producto.producto.model.ProductoDescuento;
import com.producto.producto.repository.ProductoDescuentoRepository;

@Service
public class ProductoDescuentoService {

    private final ProductoDescuentoRepository repository;

    public ProductoDescuentoService(ProductoDescuentoRepository repository) {
        this.repository = repository;
    }

    public ProductoDescuento guardar(ProductoDescuento productoDescuento) {
        return repository.save(productoDescuento);
    }
}

