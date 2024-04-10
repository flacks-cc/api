package com.tutorial.crud.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.TicketDto;
import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.MetodoPago;
import com.tutorial.crud.entity.Ticket;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.DetalleProductoService;
import com.tutorial.crud.service.MetodoPagoService;
import com.tutorial.crud.service.ProductoService;
import com.tutorial.crud.service.ReservaService;
import com.tutorial.crud.service.ServicioService;
import com.tutorial.crud.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

	@Autowired
	TicketService ticketService;

	@Autowired
	ProductoService productoService;
	
	@Autowired
	ServicioService servicioService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	MetodoPagoService metodoPagoService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createTicketAsAdmin")
	public ResponseEntity<?> createTicketAsAdmin(@RequestBody TicketDto ticketDto) {
		// Obtener el detalle producto a partir del ID proporcionado
		DetalleProducto detalleProducto = detalleProductoService.findById(ticketDto.getIdDetalleProducto())
				.orElseThrow(() -> new IllegalArgumentException("El detalle producto especificado no existe"));

		// Obtener el método de pago a partir del ID proporcionado
		MetodoPago metodoPago = metodoPagoService.findById(ticketDto.getMetodoPago().getIdMetodoPago())
				.orElseThrow(() -> new IllegalArgumentException("El método de pago especificado no existe"));

		// Obtener el usuario a partir del ID proporcionado
		Usuario usuario = usuarioService.findById(ticketDto.getEmpleado().getIdUsuario())
				.orElseThrow(() -> new IllegalArgumentException("El usuario especificado no existe"));

		// Asignar el total del DetalleProducto al campo montoTotal del TicketDto
		ticketDto.setMontoTotal(detalleProducto.getTotal());

		// Calcular el cambio
		double cambio = ticketDto.getMontoPagado() - ticketDto.getMontoTotal();

		// Crear el ticket con los valores proporcionados en el JSON
		Ticket ticket = new Ticket();

		ticketService.save(ticket);
		return ResponseEntity.ok(new Mensaje("Ticket creado exitosamente"));
	}

	@PostMapping("/createTicket")
	public ResponseEntity<?> createTicket(@RequestBody TicketDto ticketDto) {
		// Generar la fecha de impresión actual
		LocalDateTime fechaImpresion = LocalDateTime.now();

		// Obtener el nombre de usuario del token
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = authentication.getName();

		// Obtener el usuario a partir del nombre de usuario
		Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

		// Obtener el detalle producto a partir del ID proporcionado
		DetalleProducto detalleProducto = detalleProductoService.findById(ticketDto.getIdDetalleProducto())
				.orElseThrow(() -> new IllegalArgumentException("El detalle producto especificado no existe"));

		// Asignar el total del DetalleProducto al campo montoTotal del TicketDto
		ticketDto.setMontoTotal(detalleProducto.getTotal());

		Ticket ticket = new Ticket();

		ticketService.save(ticket);
		return ResponseEntity.ok(new Mensaje("Ticket creado exitosamente"));
	}

	@GetMapping("/getAllTickets")
	public ResponseEntity<List<Ticket>> getAllTickets() {
		List<Ticket> listaTickets = ticketService.findAll();
		return new ResponseEntity<>(listaTickets, HttpStatus.OK);
	}

	@GetMapping("/getByTicketId/{idTicket}")
	public ResponseEntity<Object> getTicketById(@PathVariable("idTicket") Long idTicket) {
		Optional<Ticket> ticketOptional = ticketService.findById(idTicket);
		if (!ticketOptional.isPresent())
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		Ticket ticket = ticketOptional.get();
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}

	@GetMapping("/getTicketDigitalByTicketId/{idTicket}")
	public ResponseEntity<Object> getTicketDigitalByTicketId(@PathVariable("idTicket") Long idTicket) {
		Optional<Ticket> ticketOptional = ticketService.findById(idTicket);
		if (!ticketOptional.isPresent())
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		Ticket ticket = ticketOptional.get();

		Map<String, Object> response = new HashMap<>();
		response.put("idTicket", ticket.getIdTicket());
		response.put("fechaImpresion", ticket.getFechaHoraExpedicion());
		response.put("montoTotal", ticket.getMontoTotal());

		Map<String, Object> detalleProducto = new HashMap<>();
		detalleProducto.put("idTicket", ticket.getDetalleProducto().getId());

		Map<String, Object> reservacion = new HashMap<>();
		reservacion.put("idTicket", ticket.getDetalleProducto().getReservacion().getId());
		reservacion.put("fechaReserva", ticket.getDetalleProducto().getReservacion().getFechaReserva());
		reservacion.put("horaInicio", ticket.getDetalleProducto().getReservacion().getHoraInicio());
		reservacion.put("horaFin", ticket.getDetalleProducto().getReservacion().getHoraFin());

		Map<String, Object> usuario = new HashMap<>();
		usuario.put("nombre", ticket.getDetalleProducto().getReservacion().getUsuario().getNombre());
		usuario.put("apellidoPaterno", ticket.getDetalleProducto().getReservacion().getUsuario().getApellidoPaterno());
		usuario.put("telefono", ticket.getDetalleProducto().getReservacion().getUsuario().getTelefono());
		usuario.put("nombreUsuario", ticket.getDetalleProducto().getReservacion().getUsuario().getNombreUsuario());
		usuario.put("email", ticket.getDetalleProducto().getReservacion().getUsuario().getEmail());

		reservacion.put("usuario", usuario);

		Map<String, Object> servicio = new HashMap<>();
		servicio.put("nombre", ticket.getDetalleProducto().getReservacion().getServicio().getNombre());
		servicio.put("descripcion", ticket.getDetalleProducto().getReservacion().getServicio().getDescripcion());
		servicio.put("precio", ticket.getDetalleProducto().getReservacion().getServicio().getPrecio());
		servicio.put("duracion", ticket.getDetalleProducto().getReservacion().getServicio().getDuracion());

		reservacion.put("servicio", servicio);

		detalleProducto.put("reservacion", reservacion);

		Map<String, Object> producto = new HashMap<>();
		producto.put("nombre", ticket.getDetalleProducto().getProducto().getNombre());
		producto.put("descripcion", ticket.getDetalleProducto().getProducto().getDescripcion());
		producto.put("precio", ticket.getDetalleProducto().getProducto().getPrecio());
		detalleProducto.put("producto", producto);
		detalleProducto.put("cantidad", ticket.getDetalleProducto().getCantidad());

		response.put("detalleProducto", detalleProducto);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getTicketDetails/{idTicket}")
    public ResponseEntity<?> getTicketDetails(@PathVariable Long idTicket) {
        Ticket ticket = ticketService.getTicketDetails(idTicket);
        if (ticket != null) {
            return ResponseEntity.ok(ticket); // Devuelve el ticket como JSON
        } else {
            return ResponseEntity.notFound().build(); // Devuelve una respuesta 404 si no se encuentra el ticket
        }
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateTicket/{idTicket}")
	public ResponseEntity<?> updateTicket(@PathVariable("idTicket") Long idTicket, @RequestBody TicketDto ticketDto) {
		// Verificar si el ticket con el ID proporcionado existe
		Optional<Ticket> ticketOptional = ticketService.findById(idTicket);
		if (!ticketOptional.isPresent()) {
			return new ResponseEntity<>(new Mensaje("No existe un ticket con el ID proporcionado"),
					HttpStatus.NOT_FOUND);
		}

		// Obtener el ticket de la base de datos
		Ticket ticket = ticketOptional.get();

		// Obtener el detalle producto a partir del ID proporcionado en el DTO
		DetalleProducto detalleProducto = detalleProductoService.findById(ticketDto.getIdDetalleProducto())
				.orElseThrow(() -> new IllegalArgumentException("El detalle producto especificado no existe"));

		// Obtener el método de pago a partir del ID proporcionado en el DTO
		MetodoPago metodoPago = metodoPagoService.findById(ticketDto.getMetodoPago().getIdMetodoPago())
				.orElseThrow(() -> new IllegalArgumentException("El método de pago especificado no existe"));

		// Obtener el usuario a partir del ID proporcionado en el DTO
		Usuario usuario = usuarioService.findById(ticketDto.getIdUsuario())
				.orElseThrow(() -> new IllegalArgumentException("El usuario especificado no existe"));

		// Actualizar los campos del ticket con los valores proporcionados en el DTO
		ticket.setMontoTotal(detalleProducto.getTotal());
		ticket.setFechaHoraPago(LocalDateTime.now()); // Se actualiza la fecha de pago
		ticket.setMontoPagado(ticketDto.getMontoPagado());
		double cambio = ticketDto.getMontoPagado() - ticket.getMontoTotal(); // Se recalcula el cambio
		ticket.setCambio(cambio);
		ticket.setMetodoPago(metodoPago);
		ticket.setDetalleProducto(detalleProducto);
		ticket.setEmpleado(usuario);

		// Guardar el ticket actualizado en la base de datos
		ticketService.save(ticket);

		// Devolver una respuesta exitosa
		return ResponseEntity.ok(new Mensaje("Ticket actualizado exitosamente"));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteTicket/{idTicket}")
	public ResponseEntity<Object> deleteTicket(@PathVariable("idTicket") Long idTicket) {
		if (!ticketService.existsById(idTicket))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		ticketService.deleteById(idTicket);
		return new ResponseEntity<>(new Mensaje("Ticket eliminado"), HttpStatus.OK);
	}

}
