package com.producto.producto.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.producto.producto.model.Categoria;
import com.producto.producto.model.Producto;
import com.producto.producto.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
@Autowired
    private ProductoService productoService;

    public static class ErrorResponse {
        private String message;
        private int status;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<?> obtenerProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long categoriaId) {
        try {
            List<Producto> productos = productoService.getProductos(nombre, categoriaId);
            return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<?> obtenerProductoConDetalles(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.getProductoConDetalles(id));
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @GetMapping("/descuentos/{descuentoId}/productos")
    public ResponseEntity<?> obtenerProductosPorDescuento(@PathVariable Long descuentoId) {
        try {
            List<Producto> productos = productoService.getProductosByIdDescuento(descuentoId);
            return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @PostMapping("/productos")
    public ResponseEntity<?> agregarProducto(@RequestBody Producto producto) {
        try {
            return ResponseEntity.status(201).body(productoService.agregarProducto(producto));
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            return ResponseEntity.ok(productoService.actualizarProducto(id, producto));
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @PutMapping("/productos/{id}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        try {
            Integer cantidad = request.get("cantidad");
            if (cantidad == null) {
                return ResponseEntity.status(400).body(new ErrorResponse("La cantidad es obligatoria", 400));
            }
            return ResponseEntity.ok(productoService.actualizarStock(id, cantidad));
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @PutMapping("/productos/stock/bulk")
    public ResponseEntity<?> actualizarStockBulk(@RequestBody List<Map<String, Object>> updates) {
        try {
            productoService.actualizarStockBulk(updates);
            return ResponseEntity.ok().build();
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok().build();
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @GetMapping("/categorias")
    public ResponseEntity<?> obtenerCategorias() {
        try {
            List<Categoria> categorias = productoService.getCategorias();
            return categorias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @PostMapping("/categorias")
    public ResponseEntity<?> agregarCategoria(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.status(201).body(productoService.agregarCategoria(categoria));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok(productoService.actualizarCategoria(id, categoria));
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            productoService.eliminarCategoria(id);
            return ResponseEntity.ok().build();
        } catch (ProductoService.ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage(), 404));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor: " + e.getMessage(), 500));
        }
    }
}