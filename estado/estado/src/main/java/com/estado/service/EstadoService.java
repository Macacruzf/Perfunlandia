package com.estado.service;

import jakarta.transaction.Transactional;

import com.estado.model.Estado;
import com.estado.repository.EstadoRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
@Transactional
public class EstadoService {
     private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> getAllEstados() {
        List<Estado> estados = new ArrayList<>();
        estadoRepository.findAll().forEach(estados::add);
        return estados;
    }

    public Optional<Estado> getEstadoById(Long id) {
        return estadoRepository.findById(id);
    }

    public Estado createEstado(Estado estado) {
        return estadoRepository.save(estado);
    }

    public String notifyUser(Long idEstado) {
        Optional<Estado> estadoOptional = estadoRepository.findById(idEstado);

        if (estadoOptional.isPresent()) {
            Estado estado = estadoOptional.get();
            String nombre = estado.getNombre().toLowerCase();

            switch (nombre) {
                case "producto pagado":
                    return "Producto comprado con éxito";
                case "compra inválida":
                    return "Compra inválida, favor reintentar";
                case "salida a ruta aceptada":
                    return "Tu producto ha salido a ruta";
                case "en ruta":
                    return "Tu producto está en camino";
                case "producto enviado":
                    return "Tu producto fue entregado exitosamente";
                default:
                    return "Estado del producto: " + estado.getNombre();
            }

        } else {
            throw new RuntimeException("Estado con ID " + idEstado + " no encontrado");
        }
    }
}