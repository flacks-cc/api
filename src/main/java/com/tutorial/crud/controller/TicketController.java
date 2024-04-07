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
import com.tutorial.crud.entity.DetalleGeneral;
import com.tutorial.crud.entity.MetodoPago;
import com.tutorial.crud.entity.Ticket;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.DetalleGeneralService;
import com.tutorial.crud.service.MetodoPagoService;
import com.tutorial.crud.service.ProductoService;
import com.tutorial.crud.service.ReservacionService;
import com.tutorial.crud.service.TicketService;

@RestController
@Controller
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    DetalleGeneralService detalleGeneralService;
    
    @Autowired
    ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private MetodoPagoService metodoPagoService;
    
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    @GetMapping("/lista")
    public ResponseEntity<List<Ticket>> listAllTickets() {
        List<Ticket> listaTickets = ticketService.list();
        return new ResponseEntity<>(listaTickets, HttpStatus.OK);
    }
    
    @GetMapping("/detalledijital/{id}")
    public ResponseEntity<Object> getTicketByIdDijital(@PathVariable("id") int id) {
        Optional<Ticket> ticketOptional = ticketService.getOne(id);
        if (!ticketOptional.isPresent())
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Ticket ticket = ticketOptional.get();

        Map<String, Object> response = new HashMap<>();
        response.put("id", ticket.getId());
        response.put("fechaImpresion", ticket.getFechaImpresion());
        response.put("montoTotal", ticket.getMontoTotal());

        Map<String, Object> detalleGeneral = new HashMap<>();
        detalleGeneral.put("id", ticket.getDetalleGeneral().getId());

        Map<String, Object> reservacion = new HashMap<>();
        reservacion.put("id", ticket.getDetalleGeneral().getReservacion().getId());
        reservacion.put("fechaReserva", ticket.getDetalleGeneral().getReservacion().getFechaReserva());
        reservacion.put("horaInicio", ticket.getDetalleGeneral().getReservacion().getHoraInicio());
        reservacion.put("horaFin", ticket.getDetalleGeneral().getReservacion().getHoraFin());

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", ticket.getDetalleGeneral().getReservacion().getUsuario().getNombre());
        usuario.put("apellidoPaterno", ticket.getDetalleGeneral().getReservacion().getUsuario().getApellidoPaterno());
        usuario.put("telefono", ticket.getDetalleGeneral().getReservacion().getUsuario().getTelefono());
        usuario.put("nombreUsuario", ticket.getDetalleGeneral().getReservacion().getUsuario().getNombreUsuario());
        usuario.put("email", ticket.getDetalleGeneral().getReservacion().getUsuario().getEmail());

        reservacion.put("usuario", usuario);

        Map<String, Object> servicio = new HashMap<>();
        servicio.put("nombre", ticket.getDetalleGeneral().getReservacion().getServicio().getNombre());
        servicio.put("descripcion", ticket.getDetalleGeneral().getReservacion().getServicio().getDescripcion());
        servicio.put("precio", ticket.getDetalleGeneral().getReservacion().getServicio().getPrecio());
        servicio.put("duracion", ticket.getDetalleGeneral().getReservacion().getServicio().getDuracion());

        reservacion.put("servicio", servicio);

        detalleGeneral.put("reservacion", reservacion);

        Map<String, Object> producto = new HashMap<>();
        producto.put("nombre", ticket.getDetalleGeneral().getProducto().getNombre());
        producto.put("descripcion", ticket.getDetalleGeneral().getProducto().getDescripcion());
        producto.put("precio", ticket.getDetalleGeneral().getProducto().getPrecio());

        Map<String, Object> categoria = new HashMap<>();
        categoria.put("nombre", ticket.getDetalleGeneral().getProducto().getCategoria().getNombre());
        categoria.put("descripcion", ticket.getDetalleGeneral().getProducto().getCategoria().getDescripcion());

        producto.put("categoria", categoria);

        detalleGeneral.put("producto", producto);
        detalleGeneral.put("cantidad", ticket.getDetalleGeneral().getCantidad());

        response.put("detalleGeneral", detalleGeneral);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/detalle/{id}")
    public ResponseEntity<Object> getTicketById(@PathVariable("id") int id) {
        Optional<Ticket> ticketOptional = ticketService.getOne(id);
        if (!ticketOptional.isPresent())
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Ticket ticket = ticketOptional.get();
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> deleteTicket(@PathVariable("id") int id) {
        if (!ticketService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        ticketService.delete(id);
        return new ResponseEntity<>(new Mensaje("Ticket eliminado"), HttpStatus.OK);
    }
    

    @PostMapping("/crearusuario")
    public ResponseEntity<?> crearUsuario(@RequestBody TicketDto ticketDto) {
        // Generar la fecha de impresión actual
        LocalDateTime fechaImpresion = LocalDateTime.now();
        
        // Obtener el nombre de usuario del token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        
        // Obtener el usuario a partir del nombre de usuario
        Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
        
        // Obtener el detalle general a partir del ID proporcionado
        DetalleGeneral detalleGeneral = detalleGeneralService.findById(ticketDto.getIdDetalleGeneral())
                .orElseThrow(() -> new IllegalArgumentException("El detalle general especificado no existe"));
        
        // Asignar el total del DetalleGeneral al campo montoTotal del TicketDto
        ticketDto.setMontoTotal(detalleGeneral.getTotal());
        

        Ticket ticket = new Ticket(
                fechaImpresion,
                ticketDto.getMontoTotal(),
                detalleGeneral,
                usuario
        );
        
        // Guardar el ticket en la base de datos
        ticketService.save(ticket);
        
        // Devolver una respuesta exitosa
        return ResponseEntity.ok(new Mensaje("Ticket creado exitosamente"));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crearadmin")
    public ResponseEntity<?> crearAdmin(@RequestBody TicketDto ticketDto) {
        // Obtener el detalle general a partir del ID proporcionado
        DetalleGeneral detalleGeneral = detalleGeneralService.findById(ticketDto.getIdDetalleGeneral())
                .orElseThrow(() -> new IllegalArgumentException("El detalle general especificado no existe"));
        
        // Obtener el método de pago a partir del ID proporcionado
        MetodoPago metodoPago = metodoPagoService.findById(ticketDto.getIdMetodoPago())
                .orElseThrow(() -> new IllegalArgumentException("El método de pago especificado no existe"));
        
        // Obtener el usuario a partir del ID proporcionado
        Usuario usuario = usuarioService.findById(ticketDto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("El usuario especificado no existe"));
        
        // Asignar el total del DetalleGeneral al campo montoTotal del TicketDto
        ticketDto.setMontoTotal(detalleGeneral.getTotal());
        
        // Calcular el cambio
        double cambio = ticketDto.getMontoPagado() - ticketDto.getMontoTotal();
        
        // Crear el ticket con los valores proporcionados en el JSON
        Ticket ticket = new Ticket(
                LocalDateTime.now(),  // Fecha de impresión automática
                ticketDto.getMontoTotal(),
                LocalDateTime.now(),  // Fecha de pago automática
                ticketDto.getMontoPagado(),
                cambio,  // Calculado automáticamente
                ticketDto.getNombreEmpleado(),
                metodoPago,
                detalleGeneral,
                usuario
        );
        
        // Guardar el ticket en la base de datos
        ticketService.save(ticket);
        
        // Devolver una respuesta exitosa
        return ResponseEntity.ok(new Mensaje("Ticket creado exitosamente"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTicket(@PathVariable("id") int id, @RequestBody TicketDto ticketDto) {
        // Verificar si el ticket con el ID proporcionado existe
        Optional<Ticket> ticketOptional = ticketService.getOne(id);
        if (!ticketOptional.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No existe un ticket con el ID proporcionado"), HttpStatus.NOT_FOUND);
        }
        
        // Obtener el ticket de la base de datos
        Ticket ticket = ticketOptional.get();
        
        // Obtener el detalle general a partir del ID proporcionado en el DTO
        DetalleGeneral detalleGeneral = detalleGeneralService.findById(ticketDto.getIdDetalleGeneral())
                .orElseThrow(() -> new IllegalArgumentException("El detalle general especificado no existe"));
        
        // Obtener el método de pago a partir del ID proporcionado en el DTO
        MetodoPago metodoPago = metodoPagoService.findById(ticketDto.getIdMetodoPago())
                .orElseThrow(() -> new IllegalArgumentException("El método de pago especificado no existe"));
        
        // Obtener el usuario a partir del ID proporcionado en el DTO
        Usuario usuario = usuarioService.findById(ticketDto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("El usuario especificado no existe"));
        
        // Actualizar los campos del ticket con los valores proporcionados en el DTO
        ticket.setMontoTotal(detalleGeneral.getTotal());
        ticket.setNombreEmpleado(ticketDto.getNombreEmpleado());
        ticket.setFechaPago(LocalDateTime.now());  // Se actualiza la fecha de pago
        ticket.setMontoPagado(ticketDto.getMontoPagado());
        double cambio = ticketDto.getMontoPagado() - ticket.getMontoTotal();  // Se recalcula el cambio
        ticket.setCambio(cambio);
        ticket.setMetodoPago(metodoPago);
        ticket.setDetalleGeneral(detalleGeneral);
        ticket.setUsuario(usuario);
        
        // Guardar el ticket actualizado en la base de datos
        ticketService.save(ticket);
        
        // Devolver una respuesta exitosa
        return ResponseEntity.ok(new Mensaje("Ticket actualizado exitosamente"));
    }


}
