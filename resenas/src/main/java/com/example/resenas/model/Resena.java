package com.example.resenas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "resenas")
@Data

public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @Column(nullable = false, length = 500)
    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private Integer calificacion; 

    @Column(name = "usuarioId", nullable = false)
    private Long idUser; // ID del usuario

    @Column(name = "productoId", nullable = false)
    private Long idProducto; // ID del producto reseñado
}

