package com.example.gestionproductos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.gestionproductos.model.Productos;

@Repository
public interface ProductoRepository extends JpaRepository<Productos, Long> {

    // Consulta JPQL
    @Query("SELECT p FROM Productos p WHERE p.nombre LIKE %:nombre%")
    List<Productos> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Productos p WHERE p.categoria.id = :categoriaId")
    List<Productos> buscarPorCategoriaId(@Param("categoriaId") Long categoriaId);
}

