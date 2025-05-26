package com.example.tickets.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tickets.model.Ticket;
import com.example.tickets.service.TicketService;

@RestController
@RequestMapping(("/api/tickets"))

public class TicketController {

    private final TicketService ticketService = new TicketService();

    @PostMapping
    public Ticket crearTicket(@RequestBody Ticket ticket) {
        return ticketService.crearTicket(ticket);
    }

    @GetMapping
    public List<Ticket> listarTickets() {
        return ticketService.listarTickets();
    }

    @GetMapping("/{id}")
    public Ticket obtenerTicket(@PathVariable Long id) {
        return ticketService.obtenerTicketPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
    }
}