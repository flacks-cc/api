package com.tutorial.crud.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.ReservaDto;
import com.tutorial.crud.entity.Reserva;
import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.ReservaService;
import com.tutorial.crud.service.ServicioService;

@RestController
@RequestMapping("/api/reserva")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ServicioService servicioService;

	// Agrega una reservación como cliente
	@PostMapping("/createReserve")
	public ResponseEntity<?> createReserve(@RequestBody ReservaDto reservaDto) {
		// Obtener el nombre de usuario del token
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String nombreUsuario = authentication.getName();
	    Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
	            .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
	    // Obtener el servicio a partir del ID proporcionado, si existe
	    Servicio servicio = null;
	    if (reservaDto.getIdServicio() != null) {
	        Long idServicio = reservaDto.getIdServicio().longValue();
	        servicio = servicioService.findById(idServicio)
	                .orElseThrow(() -> new IllegalArgumentException("El servicio especificado no existe"));
	    }
		// Obtener el usuario cliente a partir del nombre de usuario
		Usuario usuarioCliente = usuarioService.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

		// Obtener el usuario empleado a partir del nombre de usuario
		Usuario usuarioEmpleado = usuarioService.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        // Calcular la hora de fin de la reservación
        LocalTime horaInicio = reservaDto.getHoraInicio();
        int duracionEnMinutos = servicio.getDuracion(); // Obtener la duración del servicio en minutos
        Duration duracion = Duration.ofMinutes(duracionEnMinutos); // Convertir la duración a Duration
        LocalTime horaFin = horaInicio.plus(duracion);

		// Verificar si existe alguna reservación en el intervalo de tiempo deseado
		if (reservaService.existeReservaEnIntervalo(horaInicio, horaFin)) {
			return ResponseEntity.badRequest().body(new Mensaje("Ya existe una reservación en este horario"));
		}

		// Crear la reservación
		Reserva reserva = new Reserva(reservaDto.getFecha(), horaInicio, horaFin, true, servicio, usuarioCliente, usuarioEmpleado);

		// Guardar la reservación en la base de datos
		reservaService.save(reserva);

		// Devolver una respuesta exitosa
		return ResponseEntity.ok(new Mensaje("Reservación creada exitosamente"));
	}

	// Agrega una reservación como administrador
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createReserveAsAdmin")
	public ResponseEntity<?> createReserveAsAdmin(@RequestBody @Valid ReservaDto reservaDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
			return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
		}

		// Obtener el servicio a partir del ID proporcionado
		Optional<Servicio> servicioOptional = servicioService.findById(reservaDto.getServicio().getIdServicio());
		if (!servicioOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new Mensaje("El servicio especificado no existe"));
		}

		// Obtener el usuario cliente a partir del ID proporcionado
		Optional<Usuario> usuarioClienteOptional = usuarioService.findById(reservaDto.getCliente().getIdUsuario());
		if (!usuarioClienteOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new Mensaje("El usuario especificado no existe"));
		}

		// Obtener el usuario empleado a partir del ID proporcionado
		Optional<Usuario> usuarioEmpleadoOptional = usuarioService.findById(reservaDto.getEmpleado().getIdUsuario());
		if (!usuarioEmpleadoOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new Mensaje("El usuario especificado no existe"));
		}

		// Calcular la hora de fin de la reservación
		LocalTime horaInicio = reservaDto.getHoraInicio();
		Duration duracion = servicioOptional.get().getDuracion(); // Obtener la duración del servicio
		long duracionEnMinutos = duracion.toMinutes(); // Convertir la duración a minutos
		LocalTime horaFin = horaInicio.plusMinutes(duracionEnMinutos);

		// Verificar si existe alguna reservación en el intervalo de tiempo deseado para
		// el día especificado
		boolean reservaEnIntervaloParaDia = reservaService.existeReservaEnIntervaloParaDia(horaInicio, horaFin,
				reservaDto.getFecha());

		// Verificar si existe alguna reservación en la misma hora pero en otro día
		boolean reservaEnMismaHoraOtroDia = reservaService.existeReservaEnMismaHoraOtroDia(horaInicio, horaFin,
				reservaDto.getFecha());

		// Combinar ambas validaciones
		if (reservaEnIntervaloParaDia || reservaEnMismaHoraOtroDia) {
			return ResponseEntity.badRequest().body(new Mensaje("Ya existe una reservación en este horario"));
		}

		// Crear la reservación
		Reserva reservacion = new Reserva();

		// Guardar la reservación en la base de datos
		reservaService.save(reservacion);

		// Crear un objeto que contenga los nombres correspondientes a cada ID
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("idReserva", reservacion.getIdReserva());
		respuesta.put("fecha", reservacion.getFecha());
		respuesta.put("horaInicio", reservacion.getHoraInicio());
		respuesta.put("horaFin", reservacion.getHoraFin());
		respuesta.put("nombreServicio", reservacion.getServicio().getNombre());
		respuesta.put("nombreCliente", reservacion.getCliente().getNombre());
		respuesta.put("nombreEmpleado", reservacion.getEmpleado().getNombre());

		// Devolver la respuesta con los nombres correspondientes a cada ID
		return ResponseEntity.ok(respuesta);
	}

	// Obtiene todas las reservaciones
	@GetMapping("/getAllReservations")
	public ResponseEntity<List<Reserva>> getAllReservations() {
		List<Reserva> listaReservas = reservaService.findAll();
		return new ResponseEntity<>(listaReservas, HttpStatus.OK);
	}

	// Obtiene una reservación específica por su ID
	@GetMapping("/getReserveById/{idReserva}")
	public ResponseEntity<Object> getReserveById(@PathVariable("idReserva") Long idReserva) {
		Optional<Reserva> reservaOptional = reservaService.findById(idReserva);
		if (!reservaOptional.isPresent())
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		Reserva reserva = reservaOptional.get();
		return new ResponseEntity<>(reserva, HttpStatus.OK);
	}

	// Actualiza una reservación como administrador
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateReserve/{idReserva}")
	public ResponseEntity<?> updateReserve(@PathVariable("idReserva") Long idReserva,
			@RequestBody @Valid ReservaDto reservaDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
			return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
		}

		Optional<Reserva> reservaOptional = reservaService.findById(idReserva);
		if (!reservaOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new Mensaje("La reservación no existe"));
		}

		// Obtener el servicio a partir del ID proporcionado
		Optional<Servicio> servicioOptional = servicioService.findById(reservaDto.getServicio().getIdServicio());
		if (!servicioOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new Mensaje("El servicio especificado no existe"));
		}

		// Obtener el usuario cliente a partir del ID proporcionado
		Optional<Usuario> usuarioClienteOptional = usuarioService.findById(reservaDto.getCliente().getIdUsuario());
		if (!usuarioClienteOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new Mensaje("El usuario especificado no existe"));
		}

		// Obtener el usuario empleado a partir del ID proporcionado
		Optional<Usuario> usuarioEmpleadoOptional = usuarioService.findById(reservaDto.getEmpleado().getIdUsuario());
		if (!usuarioEmpleadoOptional.isPresent()) {
			return ResponseEntity.badRequest().body(new Mensaje("El usuario especificado no existe"));
		}

		// Calcular la hora de fin de la reservación
		LocalTime horaInicio = reservaDto.getHoraInicio();
		Duration duracion = servicioOptional.get().getDuracion(); // Obtener la duración del servicio
		long duracionEnMinutos = duracion.toMinutes(); // Convertir la duración a minutos
		LocalTime horaFin = horaInicio.plusMinutes(duracionEnMinutos);

		// Verificar si existe alguna otra reservación en el intervalo de tiempo deseado
		if (reservaService.existeReservaEnIntervalo(horaInicio, horaFin)) {
			return ResponseEntity.badRequest().body(new Mensaje("Ya existe otra reservación en este horario"));
		}

		// Actualizar la reservación existente con los nuevos datos
		Reserva reserva = reservaOptional.get();
		reserva.setFecha(reservaDto.getFecha());
		reserva.setHoraInicio(horaInicio);
		reserva.setHoraFin(horaFin);
		reserva.setServicio(servicioOptional.get());
		reserva.setCliente(usuarioClienteOptional.get());
		reserva.setEmpleado(usuarioEmpleadoOptional.get());

		// Guardar la reservación actualizada en la base de datos
		reservaService.save(reserva);
		return ResponseEntity.ok(new Mensaje("Reservación actualizada exitosamente"));
	}

	// Endpoint para eliminar una reserva
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteReserve/{idReserva}")
	public ResponseEntity<Object> deleteReserve(@PathVariable("idReserva") Long idReserva) {
		if (!reservaService.existsById(idReserva))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		reservaService.deleteById(idReserva);
		return new ResponseEntity<>(new Mensaje("Reservación eliminada"), HttpStatus.OK);
	}
}
