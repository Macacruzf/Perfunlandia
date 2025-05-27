package com.example.tickets.model;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@Table(name = "tickets")
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @Column(nullable = false)
    private LocalDateTime fCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private Long idUsers;

    @Column(nullable = false)
    private String comentario;

    @OneToMany(mappedBy = "ticket")  // Asegúrate de que Mensaje tenga un campo "Ticket ticket"
    private List<Mensaje> mensajes;

    @ManyToOne
    @JoinColumn(name = "idMotivo", nullable = false)
    private Motivo motivo;
    
    // Si necesitas agregar un estado:
    @Column(nullable = true)
    private String estado;
}


