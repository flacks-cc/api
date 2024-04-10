package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	boolean existsByNombre(String nombre);
}