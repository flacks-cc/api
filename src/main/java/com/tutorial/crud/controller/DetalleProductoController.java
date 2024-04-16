package com.tutorial.crud.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tutorial.crud.dto.DetalleProductoDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Reserva;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.DetalleProductoService;
import com.tutorial.crud.service.ProductoService;
import com.tutorial.crud.service.ReservaService;

@RestController
@RequestMapping("/api/detalleProducto")
@CrossOrigin(origins = "http://localhost:4200")
public class DetalleProductoController {

	@Autowired
	DetalleProductoService detalleProductoService;

	@Autowired
	ProductoService productoService;

	@Autowired
	ReservaService reservaService;

	@Autowired
	UsuarioService usuarioService;

	@PostMapping("/createProductDetail")
	public ResponseEntity<?> crearDetalleAdmin(@RequestBody DetalleProductoDto detalleDto, @RequestParam Map<String, Object> params) {
	    Producto producto = null;
	    if (detalleDto.getIdProducto() != null) {
	        producto = productoService.findById(detalleDto.getIdProducto()).orElse(null);
	    }
	    
	    Reserva reserva = null;
	    if (detalleDto.getIdReserva() != null) {
	        reserva = reservaService.findById(detalleDto.getIdReserva()).orElse(null);
	    }
	    
	    if (reserva != null && reserva.getServicio() == null) {
	        return ResponseEntity.badRequest().body(new Mensaje("El servicio asociado a la reservación no existe"));
	    }
	    
	    double precioServicio = reserva != null ? reserva.getServicio().getPrecio() : 0;
	    
	    double subtotal = producto != null ? detalleDto.getCantidad() * producto.getPrecio() : 0;
	    
	    double total = subtotal + precioServicio;
	    
	    DetalleProducto detalle = new DetalleProducto();
	    detalle.setCantidad(detalleDto.getCantidad());
	    detalle.setTotal(total);
	    detalle.setReserva(reserva);
	    detalle.setProducto(producto);
	    
	    if (params.containsKey("producto")) {
	        params.put("producto", producto != null ? producto.getNombre() : null);
	    }
	    if (params.containsKey("servicio")) {
	        params.put("servicio", reserva != null ? reserva.getServicio().getNombre() : null);
	    }
	    
	    // Guardar el detalle del producto
	    detalleProductoService.save(detalle); 
	    
	    // Actualizar el stock del producto
	    if (producto != null) {
	        int stockActualizado = producto.getStock() - detalleDto.getCantidad();
	        producto.setStock(stockActualizado);
	        productoService.save(producto);
	    }
	    
	    // Mensaje de respuesta
	    String mensaje = "¡Detalle del producto creado exitosamente! Se ha actualizado el stock del producto.";
	    
	    return ResponseEntity.ok(new Mensaje(mensaje));
	}

	
//	{
//	  "cantidad": 2,
//	  "idReserva": 1,
//	  "idProducto": 1
//	}




	// Obtiene todos los detalles de los productos
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllProductsDetails")
	public ResponseEntity<List<Map<String, Object>>> getAllProductsDetails() {
	    List<DetalleProducto> listaDetalleProducto = detalleProductoService.findAll();
	    List<Map<String, Object>> respuesta = new ArrayList<>();

	    for (DetalleProducto detalle : listaDetalleProducto) {
	        Map<String, Object> detalleProductoMap = new HashMap<>();
	        detalleProductoMap.put("idDetalleProducto", detalle.getIdDetalleProducto());
	        detalleProductoMap.put("cantidad", detalle.getCantidad());
	        detalleProductoMap.put("total", detalle.getTotal());
	       
	        Reserva reserva = detalle.getReserva();
	        
	        Producto producto = detalle.getProducto();
	       
	        Map<String, Object> reservaMap = new HashMap<>();
	        reservaMap.put("idReserva", reserva.getIdReserva());
	        reservaMap.put("fecha", reserva.getFecha());
	        reservaMap.put("horaInicio", reserva.getHoraInicio());
	        reservaMap.put("horaFin", reserva.getHoraFin());

	        Map<String, Object> productoMap = new HashMap<>();
	        productoMap.put("idProducto", producto.getIdProducto());
	        productoMap.put("nombre", producto.getNombre());
	        productoMap.put("precio", producto.getPrecio());

	        detalleProductoMap.put("producto", productoMap);
	        detalleProductoMap.put("reserva", reservaMap);

	        respuesta.add(detalleProductoMap);
	    }

	    return ResponseEntity.ok(respuesta);
	}

	@PutMapping("/updateProductDetail/{idDetalleProducto}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateProductDetail(@PathVariable Long idDetalleProducto, @RequestBody DetalleProductoDto detalleProductoDto) {
	    try {
	        // Recuperar el detalle que se actualizará basado en el ID proporcionado
	        DetalleProducto detalleActual = detalleProductoService.findById(idDetalleProducto)
	                .orElseThrow(() -> new IllegalArgumentException("El detalle de producto especificado no existe"));
	        // Obtener el producto asociado con el detalle
	        Producto productoActual = detalleActual.getProducto();
	        // Recuperar el nuevo producto del DTO
	        Producto nuevoProducto = detalleProductoDto.getProducto();
	        // Verificar si el nuevo producto existe (si es un nuevo producto o si existe en la base de datos)
	        if (nuevoProducto.getIdProducto() != null) {
	            nuevoProducto = productoService.findById(nuevoProducto.getIdProducto())
	                    .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe"));
	        }
	        // Verificar si el stock del nuevo producto es suficiente para la cantidad actualizada
	        int nuevaCantidad = detalleProductoDto.getCantidad();
	        int nuevaCantidadDisponible = nuevoProducto.getStock();
	        if (nuevaCantidad > nuevaCantidadDisponible) {
	            return ResponseEntity.badRequest()
	                    .body(new Mensaje("No hay suficientes unidades disponibles del producto"));
	        }
	        // Actualizar el stock del producto en la base de datos
	        nuevoProducto.setStock(nuevaCantidadDisponible - nuevaCantidad);
	        productoService.save(nuevoProducto);
	        // Actualizar los campos del detalle con los valores del DTO
	        detalleActual.setCantidad(detalleProductoDto.getCantidad());
	        detalleActual.setTotal(detalleProductoDto.getTotal());
	        // Guardar el detalle actualizado en la base de datos
	        detalleProductoService.save(detalleActual);
	        // Devolver un mensaje de éxito
	        return ResponseEntity.ok(new Mensaje("Detalle de producto actualizado exitosamente"));
	    } catch (IllegalArgumentException e) {
	        // Manejar el caso en el que no se pueda encontrar el producto o el detalle
	        return ResponseEntity.badRequest().body(new Mensaje(e.getMessage()));
	    } catch (Exception e) {
	        // Manejar cualquier otra excepción que pueda ocurrir durante el proceso
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new Mensaje("Error al actualizar el detalle de producto"));
	    }
	}


	// Elimina un detalle de producto por su ID
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteProductDetail/{idDetalleProducto}")
	public ResponseEntity<Object> deleteProductDetail(@PathVariable("idDetalleProducto") Long idDetalleProducto) {
		if (detalleProductoService.existsById(idDetalleProducto)) {
			detalleProductoService.deleteById(idDetalleProducto);
			return ResponseEntity.ok(new Mensaje("Detalle de producto eliminado"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("Detalle Producto no encontrado"));
		}
	}
}
