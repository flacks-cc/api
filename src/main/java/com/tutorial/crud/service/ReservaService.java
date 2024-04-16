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
	
	 public Reserva save(Reserva reserva) {
       return reservaRepository.save(reserva);
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
	public boolean existsByFecha(String fecha) {
		return reservaRepository.existsByFecha(fecha);
	}
	
	public List<DetalleProducto> getProductosByReserva(Reserva reserva) {
        return detalleProductoRepository.findByReserva(reserva);
    }

	// Método para verificar si hay reservas existentes que se superpongan con el
	// horario deseado
    public boolean existeReservaEnIntervalo(LocalTime horaInicio, LocalTime horaFin) {
        List<Reserva> reservaciones = reservaRepository.findByHoraInicioBetweenOrHoraFinBetween(
            horaInicio, horaFin, horaInicio, horaFin);
        return !reservaciones.isEmpty();
    }

	// Método para verificar si existe alguna reservación en el intervalo de tiempo
	// deseado para el día especificado
	 // Método para verificar si existe alguna reservación en el intervalo de tiempo deseado para el día especificado
    public boolean existeReservaEnIntervaloParaDia(LocalTime horaInicio, LocalTime horaFin, LocalDate fecha) {
        List<Reserva> reservaciones = reservaRepository.findByHoraInicioBetweenAndFechaOrHoraFinBetweenAndFecha(
            horaInicio, horaFin, fecha, horaInicio, horaFin, fecha);
        return !reservaciones.isEmpty();
    }
    

	// Método en el servicio de reservaes para verificar si existe alguna
	// reservación en la misma hora pero en otro día
	 // Método en el servicio de reservaciones para verificar si existe alguna reservación en la misma hora pero en otro día
    public boolean existeReservaEnMismaHoraOtroDia(LocalTime horaInicio, LocalTime horaFin, LocalDate fecha) {
        // Verificar si existe alguna reservación en la misma hora pero en otro día
        if (horaInicio != null && horaFin != null && fecha != null) {
            return reservaRepository.existsByHoraInicioAndFecha(horaInicio, fecha.minusDays(1))
                || reservaRepository.existsByHoraFinAndFecha(horaFin, fecha.plusDays(1));
        }
        return false; // o maneja este caso según tu lógica
    }

	public boolean existeReservaEnIntervaloParaHora(LocalTime horaInicio, LocalTime horaFin) {
		// Verificar si existe alguna reservación en el intervalo de tiempo deseado para
		// la hora especificada
		return reservaRepository.existsByHoraInicioBeforeAndHoraFinAfter(horaFin, horaInicio);
	}
}
