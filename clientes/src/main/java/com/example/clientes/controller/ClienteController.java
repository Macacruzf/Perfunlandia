package com.example.clientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.clientes.model.Cliente;
import com.example.clientes.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        if (clienteService.correoExiste(cliente.getCorreoCliente())) {
            return ResponseEntity.badRequest().build(); // Correo ya registrado
        }
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.obtenerTodosClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        return clienteService.obtenerClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-run/{run}")
    public ResponseEntity<Cliente> obtenerClientePorRun(@PathVariable String run) {
        Cliente cliente = clienteService.obtenerClientePorRun(run);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable Integer id,
            @RequestBody Cliente clienteActualizado) {
        return clienteService.obtenerClientePorId(id)
                .map(clienteExistente -> {
                    clienteExistente.setNombresCliente(clienteActualizado.getNombresCliente());
                    clienteExistente.setApellidosCliente(clienteActualizado.getApellidosCliente());
                    clienteExistente.setTelefonoCliente(clienteActualizado.getTelefonoCliente());
                    return ResponseEntity.ok(clienteService.guardarCliente(clienteExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}