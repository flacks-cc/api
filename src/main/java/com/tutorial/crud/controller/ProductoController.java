package com.tutorial.crud.controller;

import java.util.ArrayList;
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
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.ProductoDto;
import com.tutorial.crud.entity.MensajeContacto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.service.ProductoService;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

	@Autowired
	ProductoService productoService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createProduct")
	public ResponseEntity<?> createProduct(@RequestBody @Valid ProductoDto productoDto, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        StringBuilder errorMessage = new StringBuilder();
	        bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
	        return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
	    }

	    // Verificar si ya existe un producto con el mismo nombre
	    if (productoService.existsByNombre(productoDto.getNombre())) {
	        return ResponseEntity.badRequest().body(new Mensaje("Ya existe un producto con el mismo nombre"));
	    }

	    // Mapeo de atributos de ProductoDto a Producto
	    Producto producto = new Producto(
	        productoDto.getNombre(),
	        productoDto.getDescripcion(),
	        productoDto.getPrecio(),
	        productoDto.getStock()
	    );

	    productoService.save(producto);
	    return ResponseEntity.ok(new Mensaje("Producto creado exitosamente"));
	}


	// Endpoint para obtener la lista de todos los productos
	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
	    List<Producto> productos = productoService.findAll();

	    List<Map<String, Object>> productosMap = new ArrayList<>();
	    for (Producto producto : productos) {
	        Map<String, Object> productoMap = new HashMap<>();
	        productoMap.put("idProducto", producto.getIdProducto());
	        productoMap.put("nombre", producto.getNombre());
	        productoMap.put("descripcion", producto.getDescripcion());
	        productoMap.put("precio", producto.getPrecio());
	        productoMap.put("stock", producto.getStock());
	        productosMap.add(productoMap);
	    }
	    return new ResponseEntity<>(productosMap, HttpStatus.OK);
	}


	 

	// Endpoint para obtener los detalles de un producto por su ID
	@GetMapping("/getProductById/{idProducto}")
	public ResponseEntity<Object> getProductById(@PathVariable("idProducto") Long idProducto) {
		if (!productoService.existsById(idProducto))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		Producto producto = productoService.findById(idProducto).get();
		return new ResponseEntity<>(producto, HttpStatus.OK);
	}

	// Endpoint para obtener los detalles de un producto por su nombre
	@GetMapping("/getProductByName/{nombre}")
	public ResponseEntity<Object> getProductByName(@PathVariable("nombre") String nombre) {
		if (!productoService.existsByNombre(nombre))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		Producto producto = productoService.findByNombre(nombre).get();
		return new ResponseEntity<>(producto, HttpStatus.OK);
	}

	// Endpoint para actualizar un producto
	@PreAuthorize("hasRole('USER') and hasRole('ADMIN')")
	@PutMapping("/updateProduct/{idProducto}")
	public ResponseEntity<Mensaje> updateProduct(@PathVariable("idProducto") Long idProducto,
	                                             @RequestBody @Valid ProductoDto productoDto,
	                                             BindingResult bindingResult) {

	    if (bindingResult.hasErrors()) {
	        StringBuilder errorMessage = new StringBuilder();
	        bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
	        return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
	    }

	    if (!productoService.existsById(idProducto)) {
	        return ResponseEntity.notFound().build();
	    }

	    Producto producto = new Producto(
	            productoDto.getNombre(),
	            productoDto.getDescripcion(),
	            productoDto.getPrecio(),
	            productoDto.getStock()
	    );

	    productoService.save(producto);

	    return ResponseEntity.ok(new Mensaje("Producto actualizado exitosamente"));
	}



	// Endpoint para eliminar un producto
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteProduct/{idProducto}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("idProducto") Long idProducto) {
		if (!productoService.existsById(idProducto))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		productoService.deleteById(idProducto);
		return new ResponseEntity<>(new Mensaje("Producto eliminado"), HttpStatus.OK);
	}
}
