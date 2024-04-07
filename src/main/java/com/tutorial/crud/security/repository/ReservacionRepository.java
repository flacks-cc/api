package com.tutorial.crud.security.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Reservacion;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Integer> {
    boolean existsByFechaReserva(String fechaReserva);

 // Define el método para buscar reservaciones en un intervalo de tiempo
    List<Reservacion> findByHoraInicioBetweenOrHoraFinBetween(
    		LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2);
}
