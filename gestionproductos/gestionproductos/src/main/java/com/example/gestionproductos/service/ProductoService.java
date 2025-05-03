package com.example.gestionproductos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestionproductos.model.Productos;
import com.example.gestionproductos.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
   
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaService categoriaService;

    // Crear producto (con validación de categoría)
    public Productos crearProducto(Productos producto) {
        if (!categoriaService.existeCategoria(producto.getCategoria().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Categoría no encontrada con ID: " + producto.getCategoria().getId()
            );
        }
        return productoRepository.save(producto);
    }

    // Actualizar producto (con validación de categoría)
    public Productos actualizarProducto(Long id, Productos productoActualizado) {
        return productoRepository.findById(id)
            .map(producto -> {
                if (!categoriaService.existeCategoria(productoActualizado.getCategoria().getId())) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Categoría no encontrada con ID: " + productoActualizado.getCategoria().getId()
                    );
                }
                producto.setNombre(productoActualizado.getNombre());
                producto.setDescripcion(productoActualizado.getDescripcion());
                producto.setPrecio(productoActualizado.getPrecio());
                producto.setStock(productoActualizado.getStock());
                producto.setCategoria(productoActualizado.getCategoria());
                return productoRepository.save(producto);
            })
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Producto no encontrado con ID: " + id
            ));
    }

    // Eliminar producto
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // Listar todos los productos
    public List<Productos> listarProductos() {
        return productoRepository.findAll();
    }

    // Buscar producto por ID
    public Productos buscarProductoPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Producto no encontrado con ID: " + id
            ));
    }

    // Buscar productos por categoría
    public List<Productos> buscarPorCategoria(Long categoriaId) {
        return productoRepository.buscarPorCategoriaId(categoriaId);
    }

    // Buscar productos por nombre
    public List<Productos> buscarPorNombre(String nombre) {
        return productoRepository.buscarPorNombre(nombre);
    }

    

}


