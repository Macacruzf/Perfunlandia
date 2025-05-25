package com.promociones.service;

import org.springframework.stereotype.Service;

import com.promociones.model.Promocion;
import com.promociones.repository.PromocionRepository;

@Service
public class PromocionService {
    private final PromocionRepository promocionRepository;

    public PromocionService(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }

    public Promocion crearPromocion(Promocion promocion) {
        return promocionRepository.save(promocion);
    }
}

