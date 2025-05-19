package com.example.gestionventas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionventas.model.Devolucion;


public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {

}
