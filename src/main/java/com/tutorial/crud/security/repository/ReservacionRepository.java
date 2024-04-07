package com.tutorial.crud.security.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Reservacion;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Integer> {
    boolean existsByFechaReserva(String fechaReserva);
    boolean existsByHoraInicioBetweenAndFechaReserva(LocalTime horaInicio, LocalTime horaFin, LocalDate fechaReserva);
    boolean existsByHoraInicioAndFechaReserva(LocalTime horaInicio, LocalDate fechaReserva);
    boolean existsByHoraFinAndFechaReserva(LocalTime horaFin, LocalDate fechaReserva);
    boolean existsByHoraInicioBeforeAndHoraFinAfter(LocalTime horaFin, LocalTime horaInicio);



 // Define el método para buscar reservaciones en un intervalo de tiempo
    List<Reservacion> findByHoraInicioBetweenOrHoraFinBetween(
    		LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2);
    
    List<Reservacion> findByHoraInicioBetweenAndFechaReservaOrHoraFinBetweenAndFechaReserva(
    	    LocalTime horaInicio1, LocalTime horaFin1, LocalDate fechaReserva1,
    	    LocalTime horaInicio2, LocalTime horaFin2, LocalDate fechaReserva2);

    
}
