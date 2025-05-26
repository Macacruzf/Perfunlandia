package com.producto.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.producto.producto.model.ProductoDescuento;

@Repository
public interface ProductoDescuentoRepository extends JpaRepository<ProductoDescuento, Long>  {

}
