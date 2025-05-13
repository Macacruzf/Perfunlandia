package com.example.gestionlogistica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionlogistica.model.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Long> {

    List<Envio> findByEstado(String estado);   //Buscar envíos por estado
    Envio findByCodigoPedido(String codigoPedido); // Buscar por código único

}
