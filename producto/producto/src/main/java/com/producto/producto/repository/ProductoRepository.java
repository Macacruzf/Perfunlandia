package com.producto.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.producto.producto.model.Producto;
import com.producto.producto.model.ProductoDescuento;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
// Método corregido: busca por nombre (ignorando mayúsculas/minúsculas)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Método corregido: busca por ID de categoría (a través de la relación "categoria.idCategoria")
    List<Producto> findByCategoria_IdCategoria(Long idCategoria); // ✅ Antes: findByIdCategoria

    // Método corregido: busca por nombre y ID de categoría
    List<Producto> findByNombreContainingIgnoreCaseAndCategoria_IdCategoria(String nombre, Long idCategoria); // ✅ Antes: ...AndIdCategoria

    // Método para buscar por estado (no requería corrección)
    List<Producto> findByIdEstado(Long idEstado);

    // Consulta personalizada para obtener descuentos por ID de descuento
    @Query("SELECT pd FROM ProductoDescuento pd WHERE pd.idDescuento = :idDescuento")
    List<ProductoDescuento> findProductoDescuentosByIdDescuento(@Param("idDescuento") Long idDescuento);
}
