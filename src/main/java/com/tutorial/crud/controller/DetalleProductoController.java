package com.tutorial.crud.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import com.tutorial.crud.dto.DetalleProductoDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Reserva;
import com.tutorial.crud.entity.Servicio;
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

	// Agrega un nuevo detalle de producto
	@PostMapping("/createProductDetail")
	public ResponseEntity<?> createProductDetail(@RequestBody DetalleProductoDto detalleProductoDto) {
		Producto producto = null;
		if (detalleProductoDto.getProducto() != null) {
			producto = productoService.findById(detalleProductoDto.getProducto().getIdProducto())
					.orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe"));
		}
		if (producto != null) {
			int nuevaCantidad = producto.getStock() - detalleProductoDto.getCantidad();
			if (nuevaCantidad < 0) {
				return ResponseEntity.badRequest()
						.body(new Mensaje("No hay suficientes unidades disponibles del producto"));
			}
			producto.setStock(nuevaCantidad);
		}
		Reserva reserva = null;
		if (detalleProductoDto.getReserva() != null) {
			reserva = reservaService.findById(detalleProductoDto.getReserva().getIdReserva())
					.orElseThrow(() -> new IllegalArgumentException("La reservación especificada no existe"));
		}
		Servicio servicio = null;
		if (reserva != null) {
			servicio = reserva.getServicio();
			if (servicio == null) {
				return ResponseEntity.badRequest().body(new Mensaje("El servicio asociado a la reservación no existe"));
			}
		}
		double precioServicio = 0;
		if (servicio != null) {
			precioServicio = servicio.getPrecio();
		}
		double subtotal = 0;
		if (producto != null) {
			subtotal = detalleProductoDto.getCantidad() * producto.getPrecio();
		}
		double total = subtotal + precioServicio;
		DetalleProducto detalle = new DetalleProducto();
		detalle.setCantidad(detalleProductoDto.getCantidad());
		detalle.setTotal(total);
		detalle.setReserva(reserva);
		detalle.setProducto(producto);
		detalleProductoService.save(detalle);
		return ResponseEntity.ok(new Mensaje("Detalle de producto creado exitosamente"));
	}

	// Obtiene todos los detalles de los productos
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllProductsDetails")
	public ResponseEntity<List<DetalleProducto>> getAllProductsDetails() {
		List<DetalleProducto> listaDetalleProducto = detalleProductoService.findAll();
		return ResponseEntity.ok(listaDetalleProducto);
	}

	// Obtiene un detalle específico de un producto por su ID
	@GetMapping("/getProductDetailById/{idDetalleProducto}")
	public ResponseEntity<Object> getProductDetailById(@PathVariable("idDetalleProducto") Long idDetalleProducto) {
		Optional<DetalleProducto> detalleProductoOptional = detalleProductoService.findById(idDetalleProducto);
		if (detalleProductoOptional.isPresent()) {
			DetalleProducto detalleProducto = detalleProductoOptional.get();
			return ResponseEntity.ok(detalleProducto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Actualiza un detalle de producto existente por su ID
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateProductDetail/{idDetalleProducto}")
	public ResponseEntity<Object> updateProductDetail(@PathVariable("idDetalleProducto") Long idDetalleProducto,
			@RequestBody DetalleProductoDto detalleProductoDto) {
		Optional<DetalleProducto> detalleOptional = detalleProductoService.findById(idDetalleProducto);
		if (detalleOptional.isPresent()) {
			DetalleProducto detalle = detalleOptional.get();
			detalle.setCantidad(detalleProductoDto.getCantidad());
			Producto producto = null;
			if (detalleProductoDto.getProducto() != null) {
				producto = productoService.findById(detalleProductoDto.getProducto().getIdProducto())
						.orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe"));
				detalle.setProducto(producto);
			}
			Reserva reserva = null;
			if (detalleProductoDto.getReserva() != null) {
				reserva = reservaService.findById(detalleProductoDto.getReserva().getIdReserva())
						.orElseThrow(() -> new IllegalArgumentException("La reservación especificada no existe"));
				detalle.setReserva(reserva);
			}
			double precioProducto = 0;
			if (producto != null) {
				precioProducto = producto.getPrecio();
			}
			double precioServicio = 0;
			if (reserva != null && reserva.getServicio() != null) {
				precioServicio = reserva.getServicio().getPrecio();
			}
			double subtotal = detalleProductoDto.getCantidad() * precioProducto;
			double total = subtotal + precioServicio;
			detalle.setTotal(total);
			detalleProductoService.save(detalle);
			return ResponseEntity.ok(new Mensaje("Detalle de producto actualizado exitosamente"));
		} else {
			return ResponseEntity.notFound().build();
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
