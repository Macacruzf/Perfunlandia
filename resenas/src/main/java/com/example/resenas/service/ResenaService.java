package com.example.resenas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.resenas.model.Resena;
import com.example.resenas.repository.ResenaRepository;

@Service

public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    public Resena crearResena(Resena resena) {
        return resenaRepository.save(resena);
    }

    public List<Resena> obtenerPorProducto(Long productoId) {
        return resenaRepository.findByProductoId(productoId);
    }

    public List<Resena> obtenerDestacadas(Integer calificacionMinima) {
        return resenaRepository.findByCalificacionGreaterThanEqual(calificacionMinima);
    }

    public Double calcularPromedio(Long productoId) {
        return resenaRepository.findByProductoId(productoId).stream()
                .mapToInt(Resena::getCalificacion)
                .average()
                .orElse(0.0);
    }
}

