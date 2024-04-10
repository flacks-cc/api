package com.tutorial.crud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.dto.DetalleProductoDto.ProductoDetailsDTO;
import com.tutorial.crud.dto.TicketDto.TicketDetailsDTO;
import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.MetodoPago;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Reserva;
import com.tutorial.crud.entity.Ticket;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.repository.TicketRepository;
import com.tutorial.crud.security.service.UsuarioService;

@Service
@Transactional
public class TicketService {

	@Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

	// Lista todos los tickets en la base de datos
	public List<Ticket> findAll() {
	    return ticketRepository.findAll();
	}

	// Encuentra un ticket por su ID
	public Optional<Ticket> findById(Long idTicket) {
		return ticketRepository.findById(idTicket);
	}

	// Encuentra un ticket por su nombre
	public Optional<Ticket> findByName(Long idTicket) {
	    return ticketRepository.findById(idTicket);
	}

	// Guarda un ticket en la base de datos
	public void save(Ticket ticket) {
	    ticketRepository.save(ticket);
	}

	// Elimina un ticket por su ID
	public void deleteById(Long idTicket) {
	    ticketRepository.deleteById(idTicket);
	}

	// Verifica si un ticket existe por su ID
	public boolean existsById(Long idTicket) {
	    return ticketRepository.existsById(idTicket);
	}

	// Verifica si un ticket existe por su nombre
	public boolean existsByNombre(String nombre) {
	    return ticketRepository.existsByNombre(nombre);
	}
	
	public Ticket getTicketDetails(Long idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).orElse(null);
        if (ticket == null) {
            return null;
        }

        TicketDetailsDTO ticketDetailsDTO = new TicketDetailsDTO();
        ticketDetailsDTO.setFechaHoraExpedicion(ticket.getFechaHoraExpedicion());
        ticketDetailsDTO.setMontoTotal(ticket.getMontoTotal());
        ticketDetailsDTO.setFechaHoraPago(ticket.getFechaHoraPago());
        ticketDetailsDTO.setMontoPagado(ticket.getMontoPagado());
        ticketDetailsDTO.setCambio(ticket.getCambio());
        
        // Obtener el nombre del método de pago
        MetodoPago metodoPago = ticket.getMetodoPago();
        if (metodoPago != null) {
            ticketDetailsDTO.setMetodoPago(metodoPago.getMetodoNombre());
        }

        // Obtener el nombre completo del empleado
        Usuario empleado = ticket.getEmpleado();
        if (empleado != null) {
            ticketDetailsDTO.setNombreCompletoEmpleado(empleado.getNombre() + " " + empleado.getApellidoPaterno() + " " + empleado.getApellidoMaterno());
        }

        // Obtener la fecha y hora de la reservación
        Reserva reserva = ticket.getReserva();
        if (reserva != null) {
            ticketDetailsDTO.setFechaReserva(reserva.getFecha());
            ticketDetailsDTO.setHoraReserva(reserva.getHoraInicio());
        }

        // Obtener el nombre y precio del servicio asociado a la reservación
        if (reserva != null && reserva.getServicio() != null) {
            ticketDetailsDTO.setNombreServicio(reserva.getServicio().getNombre());
            ticketDetailsDTO.setPrecioServicio(reserva.getServicio().getPrecio());
        }

        // Obtener los detalles de los productos asociados al ticket
        List<DetalleProducto> detallesProductos = reservaService.getProductosByReserva(reserva);
        if (detallesProductos != null && !detallesProductos.isEmpty()) {
            List<ProductoDetailsDTO> productosDTO = new ArrayList<>();
            for (DetalleProducto detalleProducto : detallesProductos) {
                Producto producto = detalleProducto.getProducto();
                ProductoDetailsDTO productoDTO = new ProductoDetailsDTO();
                productoDTO.setNombre(producto.getNombre());
                productoDTO.setPrecio(producto.getPrecio());
                productoDTO.setCantidad(detalleProducto.getCantidad());
                productosDTO.add(productoDTO);
            }
            ticketDetailsDTO.setProductos(productosDTO);
        }

        return ticket;
    }
}