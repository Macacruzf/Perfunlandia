package com.example.tickets.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(nullable = false)
    private Long idUsers;

    @Column(nullable = false)
    private String comentario;

    @OneToMany
    @JoinColumn(nullable = false)
    private Long idMensaje;
   
    @ManyToOne
    @JoinColumn(nullable = false)
    private Long idMotivo;


    //@ManyToOne
    //@JoinColumn(name = "id_categoria", referencedColumnName = "idCategoria", nullable = false)
    //private Categoria categoria;



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
