package com.example.soportes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soportes.model.Soporte;
import com.example.soportes.repository.SoporteRepository;

@Service
public class SoporteService {

    @Autowired
    private SoporteRepository soporteRepository;

    public Soporte crearTicket(Soporte soporte) {
        soporte.setEstado("ABIERTO");
        return soporteRepository.save(soporte);
    }

    public Soporte actualizarEstado(Long id, String nuevoEstado) {
        Soporte soporte = soporteRepository.findById(id).orElseThrow();
        soporte.setEstado(nuevoEstado);
        if ("CERRADO".equals(nuevoEstado)) {
            soporte.setFechaCierre(LocalDateTime.now());
        }
        return soporteRepository.save(soporte);
    }

    public List<Soporte> obtenerTodos() {
        return soporteRepository.findAll();
    }

    public Optional<Soporte> buscarPorId(Long id) {
        return soporteRepository.findById(id);
    }
}