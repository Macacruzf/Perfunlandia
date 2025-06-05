package com.estado.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.estado.model.Estado;
import com.estado.repository.EstadoRepository;
@Configuration
public class LoadDataBase {
    @Bean
    CommandLineRunner initDatabase(EstadoRepository repository) {
        return args -> {
            repository.save(new Estado(null, 1L, 1001L, "Compra realizada con éxito", "Compra realizada", "2025-06-05"));
            repository.save(new Estado(null, 1L, 1001L, "Producto está en camino", "En ruta", "2025-06-06"));
            repository.save(new Estado(null, 2L, 1002L, "Producto preparado para envío", "Salida a ruta aceptada", "2025-06-05"));
        };
    }

}
