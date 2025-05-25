package com.example.gestionventas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.gestionventas.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

}
