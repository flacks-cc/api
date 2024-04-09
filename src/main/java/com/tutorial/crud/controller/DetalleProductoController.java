package com.tutorial.crud.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tutorial.crud.dto.DetalleProductoDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Reservacion;
import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.DetalleProductoService;
import com.tutorial.crud.service.ProductoService;
import com.tutorial.crud.service.ReservacionService;

@RestController
@RequestMapping("/detalleproducto")
@CrossOrigin(origins = "http://localhost:4200")
public class DetalleProductoController {

    @Autowired
    DetalleProductoService detalleProductoService;
    
    @Autowired
    ProductoService productoService;
    
    @Autowired
    private ReservacionService reservacionService;

    @Autowired
    private UsuarioService usuarioService;
    
   

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<DetalleProducto>> listAllDetalleProducto() {
        List<DetalleProducto> listaDetalleProducto = detalleProductoService.list();
        return ResponseEntity.ok(listaDetalleProducto);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Object> getDetalleProductoById(@PathVariable("id") int id) {
        Optional<DetalleProducto> detalleProductoOptional = detalleProductoService.getOne(id);
        if (detalleProductoOptional.isPresent()) {
            DetalleProducto detalleProducto = detalleProductoOptional.get();
            return ResponseEntity.ok(detalleProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminarDetalle/{id}")
    public ResponseEntity<Object> deleteDetalleProducto(@PathVariable("id") int id) {
        if (detalleProductoService.existsById(id)) {
            detalleProductoService.delete(id);
            return ResponseEntity.ok(new Mensaje("Detalle Producto eliminado"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("Detalle Producto no encontrado"));
        }
    }
    
    @PostMapping("/creardetalle")
    public ResponseEntity<?> crearDetalle(@RequestBody DetalleProductoDto detalleDto) {
        // Obtener el nombre de usuario del token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        
        // Obtener el usuario a partir del nombre de usuario
        Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
        
        // Obtener el producto a partir del ID proporcionado, si existe
        Producto producto = null;
        if (detalleDto.getIdProducto() != null) {
            producto = productoService.findById(detalleDto.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe"));
        }
        // Reducir la cantidad del producto adquirida en el detalle de la cantidad total del producto, si existe
        if (producto != null) {
            int nuevaCantidad = producto.getCantidadTotal() - detalleDto.getCantidad();
            if (nuevaCantidad < 0) {
                return ResponseEntity.badRequest().body(new Mensaje("No hay suficientes unidades disponibles del producto"));
            }
            producto.setCantidadTotal(nuevaCantidad);
        }
        // Obtener la reservación a partir del ID proporcionado, si existe
        Reservacion reservacion = null;
        if (detalleDto.getIdReservacion() != null) {
            reservacion = reservacionService.findById(detalleDto.getIdReservacion())
                    .orElseThrow(() -> new IllegalArgumentException("La reservación especificada no existe"));
        }
        // Obtener el servicio asociado a la reservación, si existe
        Servicio servicio = null;
        if (reservacion != null) {
            servicio = reservacion.getServicio();
            if (servicio == null) {
                return ResponseEntity.badRequest().body(new Mensaje("El servicio asociado a la reservación no existe"));
            }
        }
        // Obtener el precio del servicio, si existe
        double precioServicio = 0;
        if (servicio != null) {
            precioServicio = servicio.getPrecio();
        }
        // Calcular el subtotal como cantidad * precio de Producto, si existe
        double subtotal = 0;
        if (producto != null) {
            subtotal = detalleDto.getCantidad() * producto.getPrecio();
        }
        // Calcular el total como subtotal + precio de Servicio
        double total = subtotal + precioServicio;
        // Crear el detalle utilizando el constructor adecuado
        DetalleProducto detalle = new DetalleProducto();
        detalle.setCantidad(detalleDto.getCantidad());
        detalle.setTotal(total);
        detalle.setReservacion(reservacion);
        detalle.setProducto(producto);
        detalle.setUsuario(usuario);
        // Guardar el detalle
        detalleProductoService.save(detalle); 
        // Devolver una respuesta exitosa
        return ResponseEntity.ok(new Mensaje("Detalle creado exitosamente"));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizardetalle/{id}")
    public ResponseEntity<Object> actualizarDetalle(@PathVariable("id") int id, @RequestBody DetalleProductoDto detalleDto) {
        Optional<DetalleProducto> detalleOptional = detalleProductoService.getOne(id);
        if (detalleOptional.isPresent()) {
            DetalleProducto detalle = detalleOptional.get();
            
            // Actualizar los campos del detalle con los nuevos datos
            detalle.setCantidad(detalleDto.getCantidad());
            
            // Obtener el producto a partir del ID proporcionado, si existe
            Producto producto = null;
            if (detalleDto.getIdProducto() != null) {
                producto = productoService.findById(detalleDto.getIdProducto())
                        .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe"));
                detalle.setProducto(producto);
            }
            
            // Obtener la reservación a partir del ID proporcionado, si existe
            Reservacion reservacion = null;
            if (detalleDto.getIdReservacion() != null) {
                reservacion = reservacionService.findById(detalleDto.getIdReservacion())
                        .orElseThrow(() -> new IllegalArgumentException("La reservación especificada no existe"));
                detalle.setReservacion(reservacion);
            }
            
            // Calcular el total
            double precioProducto = 0;
            if (producto != null) {
                precioProducto = producto.getPrecio();
            }
            
            double precioServicio = 0;
            if (reservacion != null && reservacion.getServicio() != null) {
                precioServicio = reservacion.getServicio().getPrecio();
            }
            
            double subtotal = detalleDto.getCantidad() * precioProducto;
            double total = subtotal + precioServicio;
            detalle.setTotal(total);
            
            // Guardar el detalle actualizado
            detalleProductoService.save(detalle);

            return ResponseEntity.ok(new Mensaje("Detalle actualizado exitosamente"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
