package com.promociones.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.promociones.model.Descuento;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {

    Optional<Descuento> findByCodigo(String codigo);
    @Query("SELECT d FROM Descuento d WHERE d.promocion.fechaExpiracion >= :fechaActual")
    List<Descuento> findDescuentosActivosPorFecha(@Param("fechaActual") LocalDate fechaActual);
}