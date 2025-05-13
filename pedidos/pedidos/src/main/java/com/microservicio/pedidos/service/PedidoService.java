package com.microservicio.pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.microservicio.pedidos.model.Pedido;

import com.microservicio.pedidos.repository.PedidoRepository;


import java.util.Optional;
@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    // Crear un nuevo pedido
    public Pedido crearPedido(Pedido pedido) {
        return pedidoRepository.save(pedido); // Guarda el pedido en la base de datos
    }

    // Obtener un pedido por su ID
    public Pedido obtenerPedido(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElse(null); // Devuelve el pedido si existe, o null si no se encuentra
    }

    // Actualizar un pedido existente
    public Pedido actualizarPedido(Long id, Pedido pedido) {
        if (pedidoRepository.existsById(id)) {
            pedido.setId(id); // Asegura que el ID del pedido es el correcto
            return pedidoRepository.save(pedido); // Guarda el pedido actualizado
        }
        return null; // Si no se encuentra el pedido, devuelve null
    }

    // Eliminar un pedido por su ID
    public boolean eliminarPedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id); // Elimina el pedido de la base de datos
            return true; // Devuelve true si se ha eliminado con éxito
        }
        return false; // Devuelve false si no se encuentra el pedido
    }
}
