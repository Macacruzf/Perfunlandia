package com.estado.service;

import jakarta.transaction.Transactional;

import com.estado.model.Estado;
import com.estado.repository.EstadoRepository;
import com.estado.webclient.PrivilegioClient;
import com.estado.webclient.ProductoClient;
import com.estado.webclient.UsuarioClient;
import com.estado.webclient.VentaClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
@Transactional
public class EstadoService {
    private final EstadoRepository estadoRepository;
    private final ProductoClient productoClient;
    private final UsuarioClient usuarioClient;
    private final PrivilegioClient privilegioClient;
    private final VentaClient ventaClient;

    public EstadoService(EstadoRepository estadoRepository, ProductoClient productoClient, UsuarioClient usuarioClient, PrivilegioClient privilegioClient, VentaClient ventaClient) {
        this.estadoRepository = estadoRepository;
        this.productoClient = productoClient;
        this.usuarioClient = usuarioClient;
        this.privilegioClient = privilegioClient;
        this.ventaClient = ventaClient;
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

    public void notifyOtherMicroservices(Long idEstado) {
        productoClient.notifyProducto(idEstado);
        usuarioClient.notifyUsuario(idEstado);
        privilegioClient.notifyPrivilegio(idEstado);
        ventaClient.notifyVenta(idEstado);
    }
}