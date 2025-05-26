package com.promociones.service;


import java.time.LocalDate;
import java.util.List;


import org.springframework.stereotype.Service;
import com.promociones.model.Descuento;
import com.promociones.model.Promocion;
import com.promociones.repository.DescuentoRepository;
import com.promociones.repository.PromocionRepository;
import com.promociones.webclient.ProductoClient;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class DescuentoService {

    private final DescuentoRepository descuentoRepository;
    private final PromocionRepository promocionRepository;
    private final ProductoClient productoClient;

    public DescuentoService(DescuentoRepository descuentoRepository,
                            PromocionRepository promocionRepository,
                            ProductoClient productoClient) {
        this.descuentoRepository = descuentoRepository;
        this.promocionRepository = promocionRepository;
        this.productoClient = productoClient;
    }

    public Descuento crearDescuento(Descuento descuento, Long idProducto) {
        Promocion promocion = promocionRepository.findById(descuento.getPromocion().getIdPromocion())
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
        descuento.setPromocion(promocion);

        Descuento savedDescuento = descuentoRepository.save(descuento);
        productoClient.asociarDescuentoAProducto(idProducto, savedDescuento.getIdDescuento());

        return savedDescuento;
    }

    public Descuento obtenerDescuentoPorCodigo(String codigo) {
        Descuento descuento = descuentoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Código de descuento no válido o no existe"));

        if (descuento.getPromocion().getFechaExpiracion().isBefore(LocalDate.now())) {
            throw new RuntimeException("El descuento ha expirado");
        }

        return descuento;
    }

    public List<Descuento> obtenerDescuentosActivosPorFecha(LocalDate fechaActual) {
        return descuentoRepository.findDescuentosActivosPorFecha(fechaActual);
    }
}
