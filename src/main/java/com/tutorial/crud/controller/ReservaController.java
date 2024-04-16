package com.tutorial.crud.controller;


import java.time.Duration;
import java.time.LocalDateTime;
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
import com.tutorial.crud.entity.Ticket;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.ReservaService;
import com.tutorial.crud.service.ServicioService;
import com.tutorial.crud.service.TicketService;

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

	@Autowired
	private TicketService ticketService; // Asegúrate de inyectar el servicio TicketService

	@PostMapping("/CreateReservationAdmin")
	public ResponseEntity<?> create(@RequestBody @Valid ReservaDto reservacionDto, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        StringBuilder errorMessage = new StringBuilder();
	        bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
	        return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
	    }

	    // Obtener el servicio a partir del ID proporcionado en el DTO
	    Optional<Servicio> servicioOptional = servicioService.findById(reservacionDto.getIdServicio());
	    if (!servicioOptional.isPresent()) {
	        return ResponseEntity.badRequest().body(new Mensaje("El servicio especificado no existe"));
	    }

	    // Obtener el usuario cliente a partir del ID proporcionado en el DTO
	    Optional<Usuario> clienteOptional = usuarioService.findById(reservacionDto.getIdCliente());
	    if (!clienteOptional.isPresent()) {
	        return ResponseEntity.badRequest().body(new Mensaje("El cliente especificado no existe"));
	    }

	    // Obtener el usuario empleado a partir del ID proporcionado en el DTO
	    Optional<Usuario> empleadoOptional = usuarioService.findById(reservacionDto.getIdEmpleado());
	    if (!empleadoOptional.isPresent()) {
	        return ResponseEntity.badRequest().body(new Mensaje("El empleado especificado no existe"));
	    }

	    Usuario empleado = empleadoOptional.get();

	    // Verificar si el usuario tiene el rol de "empleado"
	    if (!empleado.getRoles().stream().anyMatch(rol -> rol.getNombre().equals(RolNombre.ROLE_EMPLEADO))) {
	        return ResponseEntity.badRequest().body(new Mensaje("El usuario especificado no tiene el rol de empleado"));
	    }

	    // Calcular la hora de fin de la reservación
	    LocalTime horaInicio = reservacionDto.getHoraInicio();
	    int duracionEnMinutos = servicioOptional.get().getDuracion(); // Obtener la duración del servicio en minutos
	    LocalTime horaFin = horaInicio.plusMinutes(duracionEnMinutos);

	    // Verificar si existe alguna reservación en el intervalo de tiempo deseado para el día especificado
	    boolean reservaEnIntervaloParaDia = reservaService.existeReservaEnIntervaloParaDia(horaInicio, horaFin, reservacionDto.getFecha());

	    // Verificar si existe alguna reservación en la misma hora pero en otro día
	    boolean reservaEnMismaHoraOtroDia = reservaService.existeReservaEnMismaHoraOtroDia(horaInicio, horaFin, reservacionDto.getFecha());

	    // Combinar ambas validaciones
	    if (reservaEnIntervaloParaDia || reservaEnMismaHoraOtroDia) {
	        return ResponseEntity.badRequest().body(new Mensaje("Ya existe una reservación en este horario"));
	    }

	    // Crear la reservación
	    Reserva reservacion = new Reserva(
	            reservacionDto.getFecha(),
	            horaInicio,
	            horaFin,
	            servicioOptional.get(),
	            clienteOptional.get(),
	            empleadoOptional.get() 
	    );

	    // Guardar la reservación en la base de datos
	    reservacion = reservaService.save(reservacion);

	    // Crear e insertar el ticket
	    Ticket ticket = new Ticket();
	    ticket.setFechaHoraExpedicion(LocalDateTime.now()); 
	    ticket.setReserva(reservacion);

	    try {
	        ticketService.save(ticket);
	    } catch (Exception e) {
	        e.printStackTrace(); // Imprimir la pila de llamadas de la excepción
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Mensaje("Error al guardar el ticket"));
	    }

	    // Crear un objeto que contenga los nombres correspondientes a cada ID
	    Map<String, Object> respuesta = new HashMap<>();
	    respuesta.put("idReservacion", reservacion.getIdReserva());
	    respuesta.put("fechaReserva", reservacion.getFecha());
	    respuesta.put("horaInicio", reservacion.getHoraInicio());
	    respuesta.put("horaFin", reservacion.getHoraFin());
	    respuesta.put("nombreServicio", reservacion.getServicio().getNombre());
	    respuesta.put("Cliente", reservacion.getCliente().getNombre());
	    respuesta.put("Empleado", empleado); // Añade el objeto Usuario del empleado a la respuesta


	    // Devolver la respuesta con los nombres correspondientes a cada ID
	    return ResponseEntity.ok(respuesta);
	}


//	@PreAuthorize("hasRole('ADMIN')")
//	@PostMapping("/CreateReservationAdmin")
//	public ResponseEntity<?> create(@RequestBody @Valid ReservaDto reservacionDto, BindingResult bindingResult) {
//	    if (bindingResult.hasErrors()) {
//	        StringBuilder errorMessage = new StringBuilder();
//	        bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
//	        return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
//	    }
//
//	    // Obtener el servicio a partir del ID proporcionado en el DTO
//	    Optional<Servicio> servicioOptional = servicioService.findById(reservacionDto.getIdServicio());
//	    if (!servicioOptional.isPresent()) {
//	        return ResponseEntity.badRequest().body(new Mensaje("El servicio especificado no existe"));
//	    }
//
//	    // Obtener el usuario cliente a partir del ID proporcionado en el DTO
//	    Optional<Usuario> clienteOptional = usuarioService.findById(reservacionDto.getIdCliente());
//	    if (!clienteOptional.isPresent()) {
//	        return ResponseEntity.badRequest().body(new Mensaje("El cliente especificado no existe"));
//	    }
//
//	 // Obtener el usuario empleado a partir del ID proporcionado en el DTO
//	    Optional<Usuario> empleadoOptional = usuarioService.findById(reservacionDto.getIdEmpleado());
//	    if (!empleadoOptional.isPresent()) {
//	        return ResponseEntity.badRequest().body(new Mensaje("El empleado especificado no existe"));
//	    }
//
//	    Usuario empleado = empleadoOptional.get();
//
//	    // Verificar si el usuario tiene el rol de "empleado"
//	    if (!empleado.getRoles().stream().anyMatch(rol -> rol.getNombre().equals(RolNombre.ROLE_EMPLEADO))) {
//	        return ResponseEntity.badRequest().body(new Mensaje("El usuario especificado no tiene el rol de empleado"));
//	    }
//
//	    // Calcular la hora de fin de la reservación
//	    LocalTime horaInicio = reservacionDto.getHoraInicio();
//	    int duracionEnMinutos = servicioOptional.get().getDuracion(); // Obtener la duración del servicio en minutos
//	    LocalTime horaFin = horaInicio.plusMinutes(duracionEnMinutos);
//
//	    // Verificar si existe alguna reservación en el intervalo de tiempo deseado para el día especificado
//	    boolean reservaEnIntervaloParaDia = reservaService.existeReservaEnIntervaloParaDia(horaInicio, horaFin, reservacionDto.getFecha());
//
//	    // Verificar si existe alguna reservación en la misma hora pero en otro día
//	    boolean reservaEnMismaHoraOtroDia = reservaService.existeReservaEnMismaHoraOtroDia(horaInicio, horaFin, reservacionDto.getFecha());
//
//	    // Combinar ambas validaciones
//	    if (reservaEnIntervaloParaDia || reservaEnMismaHoraOtroDia) {
//	        return ResponseEntity.badRequest().body(new Mensaje("Ya existe una reservación en este horario"));
//	    }
//
//	    // Crear la reservación
//	    Reserva reservacion = new Reserva(
//	            reservacionDto.getFecha(),
//	            horaInicio,
//	            horaFin,
//	            servicioOptional.get(),
//	            clienteOptional.get(),
//	            empleadoOptional.get() 
//	    );
//
//	    // Guardar la reservación en la base de datos
//	    reservacion = reservaService.save(reservacion);
//
//	    // Crear un objeto que contenga los nombres correspondientes a cada ID
//	    Map<String, Object> respuesta = new HashMap<>();
//	    respuesta.put("idReservacion", reservacion.getIdReserva());
//	    respuesta.put("fechaReserva", reservacion.getFecha());
//	    respuesta.put("horaInicio", reservacion.getHoraInicio());
//	    respuesta.put("horaFin", reservacion.getHoraFin());
//	    respuesta.put("nombreServicio", reservacion.getServicio().getNombre());
//	    respuesta.put("Cliente", reservacion.getCliente().getNombre());
//
//	    // Devolver la respuesta con los nombres correspondientes a cada ID
//	    return ResponseEntity.ok(respuesta);
//	}
}