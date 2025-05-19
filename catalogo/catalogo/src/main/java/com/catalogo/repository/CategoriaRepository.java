package com.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.catalogo.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
