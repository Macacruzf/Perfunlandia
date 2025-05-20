package com.promociones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promociones.model.Descuento;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long>  {

}
