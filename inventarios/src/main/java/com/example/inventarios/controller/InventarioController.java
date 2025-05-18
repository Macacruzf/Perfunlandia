package com.example.inventarios.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventarios.model.Inventario;
import com.example.inventarios.service.InventarioService;

@RestController
@RequestMapping("/api/inventarios")

public class InventarioController {

    //@Autowired
    private InventarioService inventarioService;


    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerporId(@PathVariable Long id){
        try {
            //verificar su existencia
            Inventario inventario = inventarioService.getInventarioPorId(id);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Inventario> guardarInventario(@RequestBody Inventario inventario) {
        Inventario inventarioActualizado = inventarioService.actualizarStock(
            inventario.getIdProducto(),
            inventario.getSucursal().getId(), //busca el id de la sucursal desde el objeto
            inventario.getCantidad()
        );
        return ResponseEntity.status(201).body(inventarioActualizado);
    }
    
}
