package com.example.soportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soportes.model.Soporte;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Long>{

    List<Soporte> findByEstado(String estado);
    List<Soporte> findByEmailUsuario(String emailUsuario);
}
