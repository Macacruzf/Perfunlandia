package com.example.tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tickets.model.Mensaje;

@Repository

<<<<<<< HEAD
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{

    List<Mensaje> findByTicketId(Long idTicket);
    boolean existsByTicketId(Long idTicket);

=======
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    
    boolean existsByTicketId(Long ticketId);  // Verifica
    
    List<Mensaje> findByTicketId(Long ticketId);  // Busca por id  ticket
    
    List<Mensaje> findByTipo(String tipo);  // Filtra
>>>>>>> 0021a03d23ca0b1c27930f616a4203aaa111efbc
}
