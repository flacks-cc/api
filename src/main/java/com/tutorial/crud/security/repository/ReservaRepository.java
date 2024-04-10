package com.tutorial.crud.security.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	boolean existsByFechaReserva(String fechaReserva);

	boolean existsByHoraInicioBetweenAndFechaReserva(LocalTime horaInicio, LocalTime horaFin, LocalDate fechaReserva);

	boolean existsByHoraInicioAndFechaReserva(LocalTime horaInicio, LocalDate fechaReserva);

	boolean existsByHoraFinAndFechaReserva(LocalTime horaFin, LocalDate fechaReserva);

	boolean existsByHoraInicioBeforeAndHoraFinAfter(LocalTime horaFin, LocalTime horaInicio);

	// Define el método para buscar reservaciones en un intervalo de tiempo
	List<Reserva> findByHoraInicioBetweenOrHoraFinBetween(LocalTime start1, LocalTime end1, LocalTime start2,
			LocalTime end2);

	List<Reserva> findByHoraInicioBetweenAndFechaReservaOrHoraFinBetweenAndFechaReserva(LocalTime horaInicio1,
			LocalTime horaFin1, LocalDate fechaReserva1, LocalTime horaInicio2, LocalTime horaFin2,
			LocalDate fechaReserva2);
}
