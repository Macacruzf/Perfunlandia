package com.estado;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.estado.model.Estado;
import com.estado.repository.EstadoRepository;
@SpringBootApplication
public class EstadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstadoApplication.class, args);
	}

	@Bean
	CommandLineRunner precargarDatos(EstadoRepository repository) {
		return args -> {
			if (repository.count() == 0) { // Evitar duplicados
				repository.save(new Estado(null, "Pendiente", null));
				repository.save(new Estado(null, "Enviado", null));
				repository.save(new Estado(null, "Entregado", null));
			}
		};
	}
}
