package com.example.inventarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventarios.model.Inventario;
import com.example.inventarios.repository.InventarioRepository;

import jakarta.transaction.Transactional;




@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public Optional<Inventario> buscarPorId(Long idInventario) {
        return inventarioRepository.findById(idInventario);
    }

    @Transactional //para actualizar el stock (para el controller)
    public Inventario actualizarStock(Long idProducto, Long idSucursal, Integer cantidad) {
        
        Inventario inventario = inventarioRepository
            .findByIdProductoAndSucursalId(idProducto, idSucursal)
            .orElse(new Inventario(idProducto, idSucursal, null, cantidad));

        //actualiza
        inventario.setCantidad(inventario.getCantidad() + cantidad);

        //guarda
        return inventarioRepository.save(inventario);
    }
    
    public Inventario getInventarioPorId(Long id){
        return inventarioRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Inventario No encontrado"));
    }

    public List<Inventario> listarTodo() {
        return inventarioRepository.findAll();
    }

}


