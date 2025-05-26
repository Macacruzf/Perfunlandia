
package com.gestion.usuario.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.gestion.usuario.model.Rol;
import com.gestion.usuario.repository.RolRepository;


@Configuration
public class LoadDatabase {
    @Bean
  
    CommandLineRunner initDatabase(RolRepository rolRepository) {
        return args -> {
            Arrays.asList("Admin", "Cliente", "Gerente", "Logística").forEach(nombre -> {
                rolRepository.findByNombreRol(nombre).orElseGet(() -> {
                    Rol rol = new Rol();
                    rol.setNombreRol(nombre);
                    return rolRepository.save(rol);
                });
            });
        };
    }
   

}
