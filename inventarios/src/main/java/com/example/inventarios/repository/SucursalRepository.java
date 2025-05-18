package com.example.inventarios.repository;

import com.example.inventarios.model.Inventario;
import com.example.inventarios.model.Sucursal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    List<Inventario> findBySucursalId(long idSucursal);

}

