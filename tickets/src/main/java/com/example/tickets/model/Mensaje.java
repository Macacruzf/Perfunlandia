package com.example.tickets.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "mensajes")

public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idMensaje;

    @Column(nullable = false)
    private LocalDateTime fMensaje = LocalDateTime.now(); //fecha de inicio del mensaje

    @Column(nullable = false)
    private String mensaje; 
    
    @Column(nullable = false)
    private Long idTicket;  
    
    @Column(nullable = false)
    private Long idUsers;

    @Column(nullable = false)
    private String tipo;

    public void setTicket(Ticket ticket) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTicket'");
    } 
}
