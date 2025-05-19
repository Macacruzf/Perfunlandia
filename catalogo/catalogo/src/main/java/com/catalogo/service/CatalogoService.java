package com.catalogo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogo.model.Categoria;
import com.catalogo.model.Producto;
import com.catalogo.repository.CategoriaRepository;
import com.catalogo.repository.ProductoRepository;
import com.catalogo.webclient.InventarioClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatalogoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private InventarioClient inventarioClient;

    // Métodos para clientes (navegación y búsqueda)
    public List<Producto> getProductos(String nombre, Long categoriaId) {
        if (nombre != null && !nombre.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (categoriaId != null) {
            return productoRepository.findByCategoriaId(categoriaId);
        }
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Map<String, Object> getProductoConStock(Long id) {
        Producto producto = getProductoById(id);
        Map<String, Object> inventario = inventarioClient.getProductoById(id);
        return Map.of(
                "id", producto.getIdproducto(),
                "nombre", producto.getNombre(),
                "descripcion", producto.getDescripcion(),
                "precio", producto.getPrecio(),
                "categoriaId", producto.getCategoriaId(),
                "stock", inventario.get("stock")
        );
    }

    // Métodos para gestión de productos (administradores/gerentes)
    public Producto agregarProducto(Producto producto) {
        // Validar categoría
        categoriaRepository.findById(producto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        // Validar que el producto exista en el inventario
        inventarioClient.validarProducto(producto.getIdproducto());
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        Producto existente = getProductoById(id);
        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio(producto.getPrecio());
        existente.setCategoriaId(producto.getCategoriaId());
        // Validar categoría
        categoriaRepository.findById(producto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        // Validar que el producto siga existiendo en el inventario
        inventarioClient.validarProducto(id);
        return productoRepository.save(existente);
    }

    public void eliminarProducto(Long id) {
        Producto producto = getProductoById(id);
        // Nota: No se valida con inventario al eliminar, ya que el catálogo puede decidir retirar un producto de la vista
        productoRepository.delete(producto);
    }

    // Métodos para gestión de categorías
    public Categoria agregarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        existente.setNombre(categoria.getNombre());
        existente.setDescripcion(categoria.getDescripcion());
        return categoriaRepository.save(existente);
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        // Verificar que no haya productos asociados
        List<Producto> productos = productoRepository.findByCategoriaId(id);
        if (!productos.isEmpty()) {
            throw new RuntimeException("No se puede eliminar la categoría porque tiene productos asociados");
        }
        categoriaRepository.delete(categoria);
    }

    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }
}

