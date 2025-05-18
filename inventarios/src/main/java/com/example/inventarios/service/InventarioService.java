package com.example.inventarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventarios.model.Inventario;
import com.example.inventarios.repository.InventarioRepository;




@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public Optional<Inventario> buscarPorId(Long idInventario) {
        return inventarioRepository.findById(idInventario);
    }

    
    

       

}


