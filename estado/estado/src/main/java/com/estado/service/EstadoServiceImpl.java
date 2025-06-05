package com.estado.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.estado.dto.EstadoRespuestaDTO;
import com.estado.dto.ProductoDTO;
import com.estado.model.Estado;
import com.estado.repository.EstadoRepository;
import com.estado.webclient.ProductoClient;

@Service
public class EstadoServiceImpl implements EstadoService {
    private final EstadoRepository estadoRepository;
    private final ProductoClient productoClient;

    public EstadoServiceImpl(EstadoRepository estadoRepository, ProductoClient productoClient) {
        this.estadoRepository = estadoRepository;
        this.productoClient = productoClient;
    }

    @Override
    public Estado guardarEstado(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Override
    public List<EstadoRespuestaDTO> obtenerEstadosPorVenta(Long ventaId) {
        List<Estado> estados = estadoRepository.findByVentaId(ventaId);

        return estados.stream().map(estado -> {
            ProductoDTO producto = productoClient
                    .obtenerProductoPorId(estado.getProductoId())
                    .block(); // para desarrollo local

            return new EstadoRespuestaDTO(
                    estado.getVentaId(),
                    estado.getEstado(),
                    estado.getDescripcion(),
                    estado.getFechaEstado(),
                    (producto != null ? producto.getNombre() : "Producto no encontrado")
            );
        }).collect(Collectors.toList());
    }

}
