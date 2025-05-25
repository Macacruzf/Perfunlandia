package com.estado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estado.model.EstadoEnvio;

@Repository
public interface EstadoEnvioRepository extends JpaRepository<EstadoEnvio, Long> {

}
