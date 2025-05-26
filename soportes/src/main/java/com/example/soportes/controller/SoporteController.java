package com.example.soportes.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.soportes.model.Soporte;
import com.example.soportes.service.SoporteService;


@RestController
@RequestMapping("/api/soporte")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    @PostMapping
    public ResponseEntity<Soporte> crearTicket(@RequestBody Soporte soporte) { 
        return ResponseEntity.status(201).body(soporteService.crearTicket(soporte));
    }

    @GetMapping
    public ResponseEntity<List<Soporte>> obtenerTodos() { 
        List<Soporte> soportes = soporteService.obtenerTodos();
        return soportes.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(soportes);
    }

}
