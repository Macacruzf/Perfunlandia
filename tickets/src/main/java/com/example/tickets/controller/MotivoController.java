package com.example.tickets.controller;

import com.example.tickets.model.Motivo;
import com.example.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/motivos")
public class MotivoController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Motivo> crearMotivo(@RequestBody Motivo motivo) {
        Motivo nuevoMotivo = ticketService.crearMotivo(motivo);
        return ResponseEntity.ok(nuevoMotivo);
    }

    @GetMapping
    public ResponseEntity<List<Motivo>> listarMotivos() {
        List<Motivo> motivos = ticketService.listarMotivos();
        return ResponseEntity.ok(motivos);
    }
}
