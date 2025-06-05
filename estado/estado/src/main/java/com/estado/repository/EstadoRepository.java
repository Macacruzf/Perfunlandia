package com.estado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.estado.model.Estado;


public interface EstadoRepository extends JpaRepository<Estado, Long> {
     List<Estado> findByVentaId(Long ventaId);

}