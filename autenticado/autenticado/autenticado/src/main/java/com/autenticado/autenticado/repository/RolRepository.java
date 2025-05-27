package com.autenticado.autenticado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autenticado.autenticado.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombreRol(String nombreRol);


}
