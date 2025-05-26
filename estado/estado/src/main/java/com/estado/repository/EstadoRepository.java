package com.estado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estado.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}