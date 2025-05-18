package com.example.clientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clientes.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Buscar cliente por RUN
    @Query("SELECT c FROM Cliente c WHERE c.runCliente = :run")
    Cliente findByRunCliente(@Param("run") String run);

    // Busca clientes por region
    List<Cliente> findByRegionCliente(String region);

    // Verificar si el correo existe
    boolean existsByCorreoCliente(String correo);
}