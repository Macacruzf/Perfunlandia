package com.producto.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.producto.model.Categoria;
import com.producto.model.Producto;
import com.producto.repository.CategoriaRepository;
import com.producto.repository.ProductoRepository;
import com.producto.webclient.EstadoClient;
import com.producto.webclient.InventarioClient;
import com.producto.webclient.PromocionClient;
import com.producto.webclient.ReseñaClient;
import com.producto.webclient.VentaClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private InventarioClient inventarioClient;
    @Autowired
    private EstadoClient estadoClient;
    @Autowired
    private ReseñaClient reseñaClient;
    @Autowired
    private PromocionClient promocionClient;
    @Autowired
    private VentaClient ventaClient;

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

    public Map<String, Object> getProductoConDetalles(Long id) {
        Producto producto = getProductoById(id);
        Map<String, Object> estado = estadoClient.getEstadoById(producto.getId_estado());
        Map<String, Object> reseña = reseñaClient.getReseñaById(producto.getId_reseña());
        Map<String, Object> inventario = inventarioClient.getProductoById(id);
        Map<String, Object> detallesVenta = ventaClient.getDetalleVentaByProductoId(id);

        return Map.of(
                "idproducto", producto.getIdproducto(),
                "nombre", producto.getNombre(),
                "descripcion", producto.getDescripcion(),
                "precio_unitario", producto.getPrecio_unitario(),
                "stock", producto.getStock(),
                "categoriaId", producto.getCategoriaId(),
                "id_estado", producto.getId_estado(),
                "estado", estado != null ? estado.get("estado") : "Sin estado",
                "id_reseña", producto.getId_reseña(),
                "reseña", reseña != null ? reseña.get("comentario") : "Sin reseña",
                "stock_inventario", inventario != null ? inventario.get("stock") : 0,
                "detalle_venta", detallesVenta != null ? detallesVenta : "Sin detalles de venta"
        );
    }

    public Producto agregarProducto(Producto producto) {
        categoriaRepository.findById(producto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        estadoClient.validarEstado(producto.getId_estado());
        if (producto.getId_reseña() != null) {
            reseñaClient.validarReseña(producto.getId_reseña());
        }
        inventarioClient.actualizarStock(producto.getIdproducto(), producto.getStock());
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        Producto existente = getProductoById(id);
        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio_unitario(producto.getPrecio_unitario());
        existente.setStock(producto.getStock());
        existente.setCategoriaId(producto.getCategoriaId());
        existente.setId_estado(producto.getId_estado());
        existente.setId_reseña(producto.getId_reseña());
        categoriaRepository.findById(producto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        estadoClient.validarEstado(producto.getId_estado());
        if (producto.getId_reseña() != null) {
            reseñaClient.validarReseña(producto.getId_reseña());
        }
        inventarioClient.actualizarStock(id, producto.getStock());
        return productoRepository.save(existente);
    }

    public void eliminarProducto(Long id) {
        Producto producto = getProductoById(id);
        inventarioClient.eliminarStock(id);
        productoRepository.delete(producto);
    }

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
