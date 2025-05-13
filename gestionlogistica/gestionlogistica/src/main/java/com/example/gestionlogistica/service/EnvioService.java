package com.example.gestionlogistica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestionlogistica.model.Envio;
import com.example.gestionlogistica.repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> listarEnvios() {
        return envioRepository.findAll();
    }

    public Envio buscarEnvioPorId(Long id) {
        return envioRepository.findById(id).orElse(null);
    }

    public Envio guardarEnvio(Envio envio) {
        return envioRepository.save(envio);
    }

    public void borrarEnvio(Long id) {
        envioRepository.deleteById(id);
    }

    public List<Envio> buscarPorEstado(String estado) {
        return envioRepository.findByEstado(estado);
    }
}

