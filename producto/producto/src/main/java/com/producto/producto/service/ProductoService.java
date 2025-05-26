package com.producto.producto.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.producto.producto.model.Categoria;
import com.producto.producto.model.Producto;
import com.producto.producto.model.ProductoDescuento;
import com.producto.producto.repository.CategoriaRepository;
import com.producto.producto.repository.ProductoRepository;
import com.producto.producto.webclient.EstadoClient;
import com.producto.producto.webclient.ResenaClient;
import com.producto.producto.webclient.VentaClient;
import com.producto.producto.webclient.PromocionClient;

import jakarta.transaction.Transactional;

/**
 * Servicio que contiene la lógica de negocio para gestionar productos y categorías.
 * Maneja las operaciones CRUD y la integración con microservicios de estado, resenas, ventas y promociones.
 */
@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private EstadoClient estadoClient;
    
    @Autowired
    private ResenaClient resenaClient;
    
    @Autowired
    private VentaClient ventaClient;
    
    @Autowired
    private PromocionClient promocionClient;

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public List<Producto> getProductos(String nombre, Long categoriaId) {
        if (nombre != null && !nombre.isEmpty() && categoriaId != null) {
            if (!categoriaRepository.existsById(categoriaId)) {
                throw new ResourceNotFoundException("Categoría con ID " + categoriaId + " no encontrada");
            }
            return productoRepository.findByNombreContainingIgnoreCaseAndCategoria_IdCategoria(nombre, categoriaId);
        } else if (nombre != null && !nombre.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (categoriaId != null) {
            if (!categoriaRepository.existsById(categoriaId)) {
                throw new ResourceNotFoundException("Categoría con ID " + categoriaId + " no encontrada");
            }
            return productoRepository.findByCategoria_IdCategoria(categoriaId);
        }
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del producto es obligatorio");
        }
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID " + id + " no encontrado"));
    }

    public Map<String, Object> getProductoConDetalles(Long id) {
        Producto producto = getProductoById(id);
        
        Map<String, Object> estado = estadoClient.getEstadoById(producto.getIdEstado());
        if (estado == null) {
            estado = Map.of("nombre", "Estado no disponible");
        }
        List<Map<String, Object>> resenas = resenaClient.getResenasByProductoId(id);
        if (resenas == null) {
            resenas = Collections.emptyList();
        }
        List<Map<String, Object>> ventas = ventaClient.getDetallesVentaByProductoId(id);
        if (ventas == null) {
            ventas = Collections.emptyList();
        }
        List<Map<String, Object>> descuentos = procesarDescuentos(producto.getProductoDescuentos());
        String advertenciaStock = verificarStock(producto.getStock(), ventas);

        return Map.of(
            "id", producto.getIdProducto(),
            "nombre", producto.getNombre(),
            "precio", producto.getPrecioUnitario(),
            "stock", producto.getStock(),
            "categoria", producto.getCategoria().getNombre(),
            "estado", estado.getOrDefault("nombre", "Sin estado"),
            "resenas", resenas.isEmpty() ? "Sin resenas" : resenas,
            "ventas", ventas.isEmpty() ? "Sin ventas" : ventas,
            "descuentos", descuentos.isEmpty() ? "Sin descuentos" : descuentos,
            "advertencia", advertenciaStock != null ? advertenciaStock : "Stock normal"
        );
    }

    public Producto agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecioUnitario() == null || producto.getPrecioUnitario() <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor que 0");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (producto.getIdEstado() == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        if (producto.getCategoria() == null || producto.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + producto.getCategoria().getIdCategoria() + " no encontrada"));
        producto.setCategoria(categoria);
        estadoClient.validarEstado(producto.getIdEstado());
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        Producto existente = getProductoById(id);
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecioUnitario() == null || producto.getPrecioUnitario() <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor que 0");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (producto.getIdEstado() == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        if (producto.getCategoria() == null || producto.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
             .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + producto.getCategoria().getIdCategoria() + " no encontrada"));
        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecioUnitario(producto.getPrecioUnitario());
        existente.setStock(producto.getStock());
        existente.setIdEstado(producto.getIdEstado());
        existente.setCategoria(categoria);
        estadoClient.validarEstado(producto.getIdEstado());
        return productoRepository.save(existente);
    }

    public void eliminarProducto(Long id) {
        Producto producto = getProductoById(id);
        productoRepository.delete(producto);
    }

    public Producto actualizarStock(Long id, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
        Producto producto = getProductoById(id);
        int nuevoStock = producto.getStock() - cantidad;
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("Stock insuficiente");
        }
        producto.setStock(nuevoStock);
        return productoRepository.save(producto);
    }

    public void actualizarStockBulk(List<Map<String, Object>> updates) {
        if (updates == null || updates.isEmpty()) {
            throw new IllegalArgumentException("La lista de actualizaciones no puede estar vacía");
        }
        updates.forEach(update -> {
            try {
                Long productoId = Long.valueOf(update.getOrDefault("productoId", 0L).toString());
                Integer cantidad = Integer.valueOf(update.getOrDefault("cantidad", 0).toString());
                if (productoId == 0L || cantidad <= 0) {
                    throw new IllegalArgumentException("Datos inválidos en la actualización");
                }
                actualizarStock(productoId, cantidad);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Formato inválido para productoId o cantidad");
            }
        });
    }

    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria agregarCategoria(Categoria categoria) {
        if (categoria == null || categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria categoria) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la categoría es obligatorio");
        }
        if (categoria == null || categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + id + " no encontrada"));
        existente.setNombre(categoria.getNombre());
        existente.setDescripcion(categoria.getDescripcion());
        return categoriaRepository.save(existente);
    }

    public void eliminarCategoria(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la categoría es obligatorio");
        }
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID " + id + " no encontrada"));
        List<Producto> productos = productoRepository.findByCategoria_IdCategoria(id);
        if (!productos.isEmpty()) {
            throw new IllegalStateException("No se puede eliminar la categoría porque tiene " + productos.size() + " productos asociados");
        }
        categoriaRepository.delete(categoria);
    }

    public List<Producto> getProductosByIdDescuento(Long descuentoId) {
        if (descuentoId == null) {
            throw new IllegalArgumentException("El ID del descuento es obligatorio");
        }
        List<ProductoDescuento> productoDescuentos = productoRepository.findProductoDescuentosByIdDescuento(descuentoId);
        return productoDescuentos.stream()
                .map(ProductoDescuento::getProducto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> procesarDescuentos(List<ProductoDescuento> descuentos) {
        if (descuentos == null || descuentos.isEmpty()) {
            return Collections.emptyList();
        }
        return descuentos.stream()
                .map(pd -> promocionClient.getDescuentoById(pd.getIdDescuento()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private String verificarStock(Integer stock, List<Map<String, Object>> ventas) {
        int vendido = ventas.stream()
                .mapToInt(v -> {
                    Object cantidad = v.getOrDefault("cantidad", 0);
                    return (cantidad instanceof Number) ? ((Number) cantidad).intValue() : 0;
                })
                .sum();
        if (stock <= 0) return "Stock agotado";
        if (stock < vendido) return "Stock insuficiente (Necesario: " + vendido + ")";
        if (stock - vendido <= 5) return "Stock bajo (" + (stock - vendido) + " restantes)";
        return null;
    }
}