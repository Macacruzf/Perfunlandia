package com.autenticado.autenticado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autenticado.autenticado.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
     Optional<Usuario> findByCorreo(String correo);
     Optional<Usuario> findByNickname(String nickname);

}
