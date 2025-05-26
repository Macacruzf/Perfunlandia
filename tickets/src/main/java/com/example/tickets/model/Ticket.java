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
@Table(name = "tickets")

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idTicket;

    @Column(nullable = false)
    private LocalDateTime fCreacion = LocalDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idUsers;

    @Column(nullable = false)
    private String comentario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idMotivo;

    public Object getMotivo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMotivo'");
    }

    public void setMotivo(Motivo motivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMotivo'");
    }

    public void setEstado(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEstado'");
    }


    

}
