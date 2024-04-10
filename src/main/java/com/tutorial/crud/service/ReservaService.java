package com.tutorial.crud.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.Reserva;
import com.tutorial.crud.security.repository.DetalleProductoRepository;
import com.tutorial.crud.security.repository.ReservaRepository;

@Service
@Transactional
public class ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private DetalleProductoRepository detalleProductoRepository;

	// Lista todas las reservas
	public List<Reserva> findAll() {
		return reservaRepository.findAll();
	}

	// Obtiene una reserva por su ID
	public Optional<Reserva> findById(Long idReserva) {
		return reservaRepository.findById(idReserva);
	}

	// Guarda una reserva
	public void save(Reserva reserva) {
		reservaRepository.save(reserva);
	}

	// Elimina una reserva por su ID
	public void deleteById(Long idReserva) {
		reservaRepository.deleteById(idReserva);
	}

	// Verifica si una reserva existe por su ID
	public boolean existsById(Long idReserva) {
		return reservaRepository.existsById(idReserva);
	}

	// Verifica si una reserva existe por su fecha
	public boolean existsByFechaReserva(String fecha) {
		return reservaRepository.existsByFechaReserva(fecha);
	}
	
	public List<DetalleProducto> getProductosByReserva(Reserva reserva) {
        return detalleProductoRepository.findByReserva(reserva);
    }

	// Método para verificar si hay reservas existentes que se superpongan con el
	// horario deseado
	public boolean existeReservaEnIntervalo(LocalTime horaInicio, LocalTime horaFin) {
		List<Reserva> reservaes = reservaRepository.findByHoraInicioBetweenOrHoraFinBetween(horaInicio, horaFin,
				horaInicio, horaFin);
		return !reservaes.isEmpty();
	}

	// Método para verificar si existe alguna reservación en el intervalo de tiempo
	// deseado para el día especificado
	public boolean existeReservaEnIntervaloParaDia(LocalTime horaInicio, LocalTime horaFin, LocalDate fechaReserva) {
		List<Reserva> reservaes = reservaRepository
				.findByHoraInicioBetweenAndFechaReservaOrHoraFinBetweenAndFechaReserva(horaInicio, horaFin,
						fechaReserva, horaInicio, horaFin, fechaReserva);
		return !reservaes.isEmpty();
	}

	// Método en el servicio de reservaes para verificar si existe alguna
	// reservación en la misma hora pero en otro día
	public boolean existeReservaEnMismaHoraOtroDia(LocalTime horaInicio, LocalTime horaFin, LocalDate fechaReserva) {
		// Verificar si existe alguna reservación en la misma hora pero en otro día
		return reservaRepository.existsByHoraInicioAndFechaReserva(horaInicio, fechaReserva.minusDays(1))
				|| reservaRepository.existsByHoraFinAndFechaReserva(horaFin, fechaReserva.plusDays(1));
	}

	public boolean existeReservaEnIntervaloParaHora(LocalTime horaInicio, LocalTime horaFin) {
		// Verificar si existe alguna reservación en el intervalo de tiempo deseado para
		// la hora especificada
		return reservaRepository.existsByHoraInicioBeforeAndHoraFinAfter(horaFin, horaInicio);
	}
}
