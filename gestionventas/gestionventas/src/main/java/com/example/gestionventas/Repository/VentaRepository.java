package com.example.gestionventas.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.gestionventas.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Consulta JPQL
    @Query("SELECT v FROM Venta v WHERE v.runCliente = :run")
    List<Venta> buscarPorRunCliente(@Param("run") String run);

    // Consulta SQL nativa
    @Query(
        value = "SELECT * FROM venta WHERE fecha BETWEEN :fechaInicio AND :fechaFin", 
        nativeQuery = true
    )
    List<Venta> buscarPorRangoFechas(
        @Param("fechaInicio") Date fechaInicio,
        @Param("fechaFin") Date fechaFin
    );
}
