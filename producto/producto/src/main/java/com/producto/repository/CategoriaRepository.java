package com.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.producto.model.categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
