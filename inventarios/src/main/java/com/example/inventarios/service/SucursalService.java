package com.example.inventarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventarios.model.Sucursal;
import com.example.inventarios.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public Optional<Sucursal> buscarSucursalPorId(Long idSucursal) {
        return sucursalRepository.findById(idSucursal);
    }
    
    public String buscarSucursalResiliente(Long idSucursal) {
    return sucursalRepository.findById(idSucursal)
            .map(Sucursal::toString)
            .orElse("Sucursal no encontrada");
    }

    public Object guardarSucursal(Sucursal nueva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarSucursal'");
    }

    public List<Sucursal> listarSucursales() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarSucursales'");
    }  
}
