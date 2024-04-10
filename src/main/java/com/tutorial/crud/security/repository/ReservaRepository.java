package com.tutorial.crud.security.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	boolean existsByFecha(String fecha);

	boolean existsByHoraInicioBetweenAndFecha(LocalTime horaInicio, LocalTime horaFin, LocalDate fecha);

	boolean existsByHoraInicioAndFecha(LocalTime horaInicio, LocalDate fecha);

	boolean existsByHoraFinAndFecha(LocalTime horaFin, LocalDate fecha);

	boolean existsByHoraInicioBeforeAndHoraFinAfter(LocalTime horaFin, LocalTime horaInicio);

	// Define el método para buscar reservaciones en un intervalo de tiempo
	List<Reserva> findByHoraInicioBetweenOrHoraFinBetween(LocalTime start1, LocalTime end1, LocalTime start2,
			LocalTime end2);

	List<Reserva> findByHoraInicioBetweenAndFechaOrHoraFinBetweenAndFecha(LocalTime horaInicio1,
			LocalTime horaFin1, LocalDate fecha1, LocalTime horaInicio2, LocalTime horaFin2,
			LocalDate fecha2);
}
