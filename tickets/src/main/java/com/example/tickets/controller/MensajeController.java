package com.example.tickets.controller;

import com.example.tickets.model.Mensaje;
import com.example.tickets.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private TicketService mensajeService;

    //Crear mensaje en un ticket
    @PostMapping
    public Mensaje crear(@RequestBody Mensaje mensaje) {
        return mensajeService.guardar(mensaje);
    }

    //tienes todos los mensajes de un ticket
    @GetMapping("/ticket/{ticketId}")
    public List<Mensaje> listarPorTicket(@PathVariable Long ticketId) {
        return mensajeService.listarPorTicket(ticketId);
    }

    //Elimina mensaje por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        mensajeService.eliminar(id);
    }
}