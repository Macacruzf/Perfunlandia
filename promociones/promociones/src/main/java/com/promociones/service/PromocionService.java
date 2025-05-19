package com.promociones.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promociones.dto.CuponRequest;
import com.promociones.dto.CuponResponse;
import com.promociones.model.Cupon;
import com.promociones.repository.CuponRepository;
import com.promociones.webclient.PedidoClient;

@Service
public class PromocionService {
    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private PedidoClient pedidoClient;

    public List<Cupon> getAllCupones() {
        return cuponRepository.findAll();
    }

    public Cupon createCupon(Cupon cupon) {
        if (cupon.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La fecha de expiración no puede estar en el pasado");
        }
        if (cupon.getLimiteUso() <= 0) {
            throw new RuntimeException("El límite de uso debe ser mayor a cero");
        }
        if (cupon.getDescuento() <= 0) {
            throw new RuntimeException("El descuento debe ser mayor a cero");
        }
        cupon.setUsosActuales(0);
        return cuponRepository.save(cupon);
    }

    public CuponResponse applyCupon(CuponRequest request) {
        // Find coupon by code
        Cupon cupon = cuponRepository.findByCodigo(request.getCodigo())
                .orElseThrow(() -> new RuntimeException("Cupon no encontrado"));

        // Validate coupon
        if (cupon.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El cupon ha expirado");
        }
        if (cupon.getUsosActuales() >= cupon.getLimiteUso()) {
            throw new RuntimeException("El cupon ha alcanzado su límite de uso");
        }

        // Validate order exists
        Map<String, Object> pedido = pedidoClient.getPedidoById(request.getPedidoId());
        if (pedido == null || pedido.isEmpty()) {
            throw new RuntimeException("Pedido no encontrado");
        }

        // Increment usage and save
        cupon.setUsosActuales(cupon.getUsosActuales() + 1);
        cuponRepository.save(cupon);

        // Return response
        CuponResponse response = new CuponResponse();
        response.setCodigo(cupon.getCodigo());
        response.setDescuentoAplicado(cupon.getDescuento());
        response.setMensaje("Cupon aplicado exitosamente");
        return response;
    }
}
