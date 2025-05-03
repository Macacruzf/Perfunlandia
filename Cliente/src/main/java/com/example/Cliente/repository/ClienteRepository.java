package com.example.cliente.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    private List<Cliente> listaclientes = new ArrayList<>();
    //private long idCounter = 1L;

    @Autowired

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long idCliente) {
        Cliente cliente = clienteService.getClienteByidCliente(idCliente);
        return ResponseEntity.ok(cliente);
    }

    public Cliente actualizar(Cliente cliente) {
        for (int i = 0; i < listaclientes.size(); i++) {
            if (listaclientes.get(i).getIdCliente().equals(cliente.getIdCliente())) {
                listaclientes.set(i, cliente);
                return cliente;
            }
        }
        return null;
    }

    
    public Cliente buscarPorId(Long id) {
        for (Cliente cliente : listaclientes) {
            if (cliente.getIdCliente().equals(id)){
                return cliente;
            }
        }
        return null;
    }
    
    

        
}
