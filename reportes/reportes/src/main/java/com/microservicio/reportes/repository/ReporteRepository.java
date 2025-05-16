package com.microservicio.reportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservicio.reportes.model.Reporte;


@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    //Consulta JPQL
    @Query("SELECT r FROM Reporte r WHERE r.runCliente = :run")
    List<Reporte> buscarPorRunCliente(@Param("run") String run);

}
