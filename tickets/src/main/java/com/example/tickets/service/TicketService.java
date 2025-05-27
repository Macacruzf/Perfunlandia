package com.example.tickets.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tickets.model.Mensaje;
import com.example.tickets.model.Motivo;
import com.example.tickets.model.Ticket;
import com.example.tickets.repository.MensajeRepository;
import com.example.tickets.repository.MotivoRepository;
import com.example.tickets.repository.TicketRepository;


@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private MotivoRepository motivoRepository;

    public Ticket crearTicket(Ticket ticket) {
        if (ticket.getMotivo() == null || ticket.getMotivo().getIdMotivo() == null) {
            throw new RuntimeException("El motivo es obligatorio");
        }

        Motivo motivo = motivoRepository.findById(ticket.getMotivo().getIdMotivo())
                .orElseThrow(() -> new RuntimeException("Motivo no encontrado"));

        ticket.setMotivo(motivo);
        ticket.setFCreacion(LocalDateTime.now());
        ticket.setEstado("abierto"); // Asegúrate de que Ticket tenga el campo "estado"

        return ticketRepository.save(ticket);
    }

    public Ticket actualizarEstado(Long idTicket, String estado) {
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        if (!List.of("abierto", "en_proceso", "cerrado").contains(estado)) {
            throw new RuntimeException("Estado inválido");
        }

        ticket.setEstado(estado);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> listarTicketsPorUsuario(Long idUsuario) {
        return ticketRepository.findByIdUsers(idUsuario);
    }

    public void eliminarTicket(Long idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        if (mensajeRepository.existsByIdTicket(idTicket)) {
            throw new RuntimeException("No se puede eliminar: tiene mensajes asociados");
        }

        ticketRepository.delete(ticket);
    }

    public Mensaje agregarMensaje(Long idTicket, Mensaje mensaje) {
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        mensaje.setTicket(ticket); // Asegúrate que Mensaje tenga ManyToOne con Ticket
        mensaje.setFMensaje(LocalDateTime.now());

        return mensajeRepository.save(mensaje);
    }

    public List<Mensaje> listarMensajesPorTicket(Long idTicket) {
        return mensajeRepository.findByIdTicket(idTicket);
    }

    public Motivo crearMotivo(Motivo motivo) {
        return motivoRepository.save(motivo);
    }

    public List<Motivo> listarMotivos() {
        return motivoRepository.findAll();
    }

    public List<Ticket> listarTickets() {
        return ticketRepository.findAll();
    }

    public Ticket obtenerTicketPorId(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    }



    public Mensaje guardar(Mensaje mensaje) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }



    public List<Mensaje> listarPorTicket(Long ticketId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPorTicket'");
    }



    public void eliminar(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }
}
