package com.example.clientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clientes.model.Cliente;
import com.example.clientes.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Guardar cliente 
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // tener todos los clientes
    public List<Cliente> obtenerTodosClientes() {
        return clienteRepository.findAll();
    }

    // Buscar por ID
    public Optional<Cliente> obtenerClientePorId(Integer id) {
        return clienteRepository.findById(id);
    }

    // Buscar por RUN
    public Cliente obtenerClientePorRun(String run) {
        return clienteRepository.findByRunCliente(run);
    }

    // Eliminar cliente
    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    // Validar correo único
    public boolean correoExiste(String correo) {
        return clienteRepository.existsByCorreoCliente(correo);
    }
}