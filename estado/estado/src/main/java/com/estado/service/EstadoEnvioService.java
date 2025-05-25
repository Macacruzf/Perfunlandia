package com.estado.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estado.model.EstadoEnvio;
import com.estado.repository.EstadoEnvioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstadoEnvioService {
    private final EstadoEnvioRepository estadoEnvioRepository;

    public EstadoEnvioService(EstadoEnvioRepository estadoEnvioRepository) {
        this.estadoEnvioRepository = estadoEnvioRepository;
    }
    public List<EstadoEnvio> getAllEstadoEnvios() { return estadoEnvioRepository.findAll(); }
    public EstadoEnvio createEstadoEnvio(EstadoEnvio estadoEnvio) { return estadoEnvioRepository.save(estadoEnvio); }
}
