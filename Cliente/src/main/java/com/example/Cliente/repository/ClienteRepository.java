package com.example.Cliente.repository;

import com.example.Cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Buscar cliente por RUN
    @Query("SELECT c FROM Cliente c WHERE c.runCliente = :run")
    Cliente findByRunCliente(@Param("run") String run);

    // Buscar clientes por región
    List<Cliente> findByRegionCliente(String region);

    // Verificar si un correo existe
    boolean existsByCorreoCliente(String correo);
}
