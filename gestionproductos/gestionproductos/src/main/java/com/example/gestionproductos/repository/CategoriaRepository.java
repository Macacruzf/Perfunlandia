package com.example.gestionproductos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionproductos.model.Categoria;



public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByNombre(String nombre);

}
