package com.promociones.service;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.promociones.model.Descuento;
import com.promociones.model.Promocion;
import com.promociones.repository.DescuentoRepository;
import com.promociones.repository.PromocionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DescuentoService {
    private final DescuentoRepository descuentoRepository;
    private final PromocionRepository promocionRepository;
    private final WebClient productoWebClient;

    public DescuentoService(
        DescuentoRepository descuentoRepository,
        PromocionRepository promocionRepository,
        WebClient.Builder webClientBuilder,
        @Value("${producto-service.url}") String productoServiceUrl
    ) {
        this.descuentoRepository = descuentoRepository;
        this.promocionRepository = promocionRepository;
        this.productoWebClient = webClientBuilder.baseUrl(productoServiceUrl).build();
    }

    
    public Descuento crearDescuento(Descuento descuento, List<Long> productosIds) {
        // Validar que la promoción existe
        Promocion promocion = promocionRepository.findById(descuento.getPromocion().getIdPromocion())
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada"));
        descuento.setPromocion(promocion);
        descuento.setIdProducto(productosIds);

        // Guardar descuento
        Descuento savedDescuento = descuentoRepository.save(descuento);

        // Vincular descuento a productos en el microservicio de productos
        productosIds.forEach(productoId -> {
            productoWebClient.post()
                    .uri("/api/v1/producto/descuentos")
                    .bodyValue(Map.of("idDescuento", savedDescuento.getIdDescuento(), "idProducto", productoId))
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        });

        return savedDescuento;
    }
}