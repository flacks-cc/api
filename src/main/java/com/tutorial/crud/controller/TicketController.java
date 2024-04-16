package com.tutorial.crud.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	DetalleProductoService detalleProductoService;
	
    public TicketController(TicketService ticketService, DetalleProductoService detalleProductoService, MetodoPagoService metodoPagoService) {
        this.ticketService = ticketService;
        this.detalleProductoService = detalleProductoService;
        this.metodoPagoService = metodoPagoService;
    }
	
	// Endpoint para obtener todos los tickets
	@GetMapping("/getAllTickets")
	public ResponseEntity<List<Ticket>> getAllTickets() {
		List<Ticket> listaTickets = ticketService.findAll();
		return new ResponseEntity<>(listaTickets, HttpStatus.OK);
	}

	// Endpoint para obtener un ticket a través de su ID
	@GetMapping("/getByTicketId/{idTicket}")
	public ResponseEntity<Object> getTicketById(@PathVariable("idTicket") Long idTicket) {
		Optional<Ticket> ticketOptional = ticketService.findById(idTicket);
		if (!ticketOptional.isPresent())
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		Ticket ticket = ticketOptional.get();
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateTicket/{idTicket}")
	public ResponseEntity<?> updateTicket(@PathVariable("idTicket") Long idTicket, @RequestBody TicketDto ticketDto) {
	    // Verificar si el ticket con el ID proporcionado existe
	    Optional<Ticket> ticketOptional = ticketService.findById(idTicket);
	    if (!ticketOptional.isPresent()) {
	        return new ResponseEntity<>(new Mensaje("No existe un ticket con el ID proporcionado"), HttpStatus.NOT_FOUND);
	    }

	    // Obtener el ticket de la base de datos
	    Ticket ticket = ticketOptional.get();

	    // Obtener el empleado de la reserva
	    Usuario empleadoReserva = ticket.getReserva().getEmpleado();

	    // Asignar el empleado al ticket
	    ticket.setEmpleado(empleadoReserva);

	    // Obtener el método de pago a partir del DTO
	    MetodoPago metodoPago = metodoPagoService.findById(ticketDto.getIdMetodoPago())
	            .orElseThrow(() -> new IllegalArgumentException("El método de pago especificado no existe"));

	    // Obtener el detalle producto a partir del ID proporcionado en el DTO
	    DetalleProducto detalleProducto = detalleProductoService.findById(ticketDto.getIdDetalleProducto())
	            .orElseThrow(() -> new IllegalArgumentException("El detalle producto especificado no existe"));

	    // Calcular el monto total del ticket a partir del detalle del producto y la reserva
	    double montoTotal = detalleProducto.getTotal();
	    if (ticket.getReserva() != null && ticket.getReserva().getServicio() != null) {
	        montoTotal += ticket.getReserva().getServicio().getPrecio();
	    }

	    // Actualizar los campos del ticket con los valores proporcionados en el DTO
	    ticket.setMontoTotal(montoTotal);
	    ticket.setFechaHoraPago(LocalDateTime.now()); // Se actualiza la fecha de pago
	    ticket.setMontoPagado(ticketDto.getMontoPagado());
	    double cambio = ticketDto.getMontoPagado() - montoTotal; // Se recalcula el cambio
	    ticket.setCambio(cambio);
	    ticket.setMetodoPago(metodoPago);
	    ticket.setDetalleProducto(detalleProducto);

	    // Guardar el ticket actualizado en la base de datos
	    ticketService.save(ticket);

	    // Devolver una respuesta exitosa
	    return ResponseEntity.ok(new Mensaje("Ticket actualizado exitosamente"));
	}


	@GetMapping("/getTicketDigital/{idTicket}")
	public ResponseEntity<Object> getTicketDigital(@PathVariable("idTicket") Long idTicket) {
	    Optional<Ticket> ticketOptional = ticketService.findById(idTicket);
	    if (!ticketOptional.isPresent())
	        return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
	    Ticket ticket = ticketOptional.get();

	    // Construcción del JSON del ticket
	    Map<String, Object> response = new HashMap<>();
	    response.put("idTicket", ticket.getIdTicket());
	    response.put("montoTotal", ticket.getMontoTotal());
	    response.put("fechaHoraPago", ticket.getFechaHoraPago());
	    response.put("montoPagado", ticket.getMontoPagado());
	    response.put("cambio", ticket.getCambio());

	    // Construcción del JSON del método de pago
	    Map<String, Object> metodoPago = new HashMap<>();
	    metodoPago.put("idMetodoPago", ticket.getMetodoPago().getIdMetodoPago());
	    metodoPago.put("nombre", ticket.getMetodoPago().getNombre());
	    response.put("metodoPago", metodoPago);

	    // Construcción del JSON del empleado
	    Map<String, Object> empleado = new HashMap<>();
	    empleado.put("idUsuario", ticket.getEmpleado().getIdUsuario());
	    empleado.put("nombre", ticket.getEmpleado().getNombre());
	    empleado.put("apellidoPaterno", ticket.getEmpleado().getApellidoPaterno());
	    empleado.put("apellidoMaterno", ticket.getEmpleado().getApellidoMaterno());
	    response.put("empleado", empleado);

	    // Construcción del JSON de la reserva
	    Map<String, Object> reserva = new HashMap<>();
	    reserva.put("idReserva", ticket.getReserva().getIdReserva());
	    reserva.put("fecha", ticket.getReserva().getFecha());
	    reserva.put("horaInicio", ticket.getReserva().getHoraInicio());

	    // Construcción del JSON del servicio reservado
	    Map<String, Object> servicio = new HashMap<>();
	    servicio.put("idServicio", ticket.getReserva().getServicio().getIdServicio());
	    servicio.put("nombre", ticket.getReserva().getServicio().getNombre());
	    servicio.put("precio", ticket.getReserva().getServicio().getPrecio());
	    reserva.put("servicio", servicio);

	    // Construcción del JSON del cliente
	    Map<String, Object> cliente = new HashMap<>();
	    cliente.put("idUsuario", ticket.getReserva().getCliente().getIdUsuario());
	    cliente.put("nombre", ticket.getReserva().getCliente().getNombre());
	    cliente.put("apellidoPaterno", ticket.getReserva().getCliente().getApellidoPaterno());
	    cliente.put("apellidoMaterno", ticket.getReserva().getCliente().getApellidoMaterno());
	    reserva.put("cliente", cliente);

	    // Construcción del JSON del empleado de la reserva
	    Map<String, Object> empleadoReserva = new HashMap<>();
	    empleadoReserva.put("idUsuario", ticket.getReserva().getEmpleado().getIdUsuario());
	    empleadoReserva.put("nombre", ticket.getReserva().getEmpleado().getNombre());
	    empleadoReserva.put("apellidoPaterno", ticket.getReserva().getEmpleado().getApellidoPaterno());
	    empleadoReserva.put("apellidoMaterno", ticket.getReserva().getEmpleado().getApellidoMaterno());
	    reserva.put("empleado", empleadoReserva);

	    // Construcción del JSON de los detalles del producto
	    List<Map<String, Object>> detallesProductos = new ArrayList<>();
	    Map<String, Object> detalleProducto = new HashMap<>();
	    detalleProducto.put("idDetalleProducto", ticket.getDetalleProducto().getIdDetalleProducto());
	    detalleProducto.put("cantidad", ticket.getDetalleProducto().getCantidad());
	    detalleProducto.put("total", ticket.getDetalleProducto().getTotal());

	    // Construcción del JSON del producto asociado al detalle
	    Map<String, Object> producto = new HashMap<>();
	    producto.put("idProducto", ticket.getDetalleProducto().getProducto().getIdProducto());
	    producto.put("nombre", ticket.getDetalleProducto().getProducto().getNombre());
	    producto.put("precio", ticket.getDetalleProducto().getProducto().getPrecio());
	    detalleProducto.put("producto", producto);

	    detallesProductos.add(detalleProducto);
	    reserva.put("detallesProductos", detallesProductos);

	    response.put("reserva", reserva);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Endpoint para eliminar un ticket
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteTicket/{idTicket}")
	public ResponseEntity<Object> deleteTicket(@PathVariable("idTicket") Long idTicket) {
		if (!ticketService.existsById(idTicket))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		ticketService.deleteById(idTicket);
		return new ResponseEntity<>(new Mensaje("Ticket eliminado"), HttpStatus.OK);

	}
}
