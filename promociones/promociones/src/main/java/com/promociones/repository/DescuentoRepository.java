package com.promociones.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.promociones.model.Descuento;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {

    @Query("SELECT d FROM Descuento d " +
           "WHERE :productoId MEMBER OF d.productosIds " +
           "AND d.promocion.fechaExpiracion >= :fechaActual")
    List<Descuento> findDescuentosActivosPorProducto(
        @Param("productoId") Long productoId,
        @Param("fechaActual") LocalDate fechaActual
    );
}
