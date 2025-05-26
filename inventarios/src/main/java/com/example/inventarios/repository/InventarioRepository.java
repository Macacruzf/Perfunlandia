package com.example.inventarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventarios.model.Inventario;

@Repository

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findByPedidoId(Long inventarioId);

    Optional<Inventario> findByIdProductoAndSucursalId(Long idProducto, Long idSucursal); 
  

}
