package com.tutorial.crud.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.tutorial.crud.dto.ServicioDto;
import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.service.ServicioService;

@RestController
@RequestMapping("/api/servicio")
@CrossOrigin(origins = "http://localhost:4200")
public class ServicioController {

	@Autowired
	private ServicioService servicioService;

	// Endpoint para crear un nuevo servicio
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createService")
	public ResponseEntity<?> createService(@RequestBody @Valid ServicioDto servicioDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		if (servicioService.existsByNombre(servicioDto.getNombre())) {
			return ResponseEntity.badRequest().body(new Mensaje("Ya existe un servicio con el mismo nombre"));
		}
		Servicio servicio = new Servicio(servicioDto.getNombre(), servicioDto.getDescripcion(), servicioDto.getPrecio(),
				servicioDto.getDuracion());
		servicioService.save(servicio);
		return ResponseEntity.ok(new Mensaje("Servicio creado exitosamente"));
	}

	// Endpoint para obtener la lista de todos los servicios
	@GetMapping("/getAllServices")
	public ResponseEntity<List<Servicio>> getAllServices() {
		List<Servicio> listaServicios = servicioService.findAll();
		return new ResponseEntity<>(listaServicios, HttpStatus.OK);
	}

	// Endpoint para obtener los detalles de un servicio por su ID
	@GetMapping("/getServiceById/{idServicio}")
	public ResponseEntity<Object> getServiceById(@PathVariable("idServicio") Long idServicio) {
		if (!servicioService.existsById(idServicio))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		Servicio servicio = servicioService.findById(idServicio).get();
		return new ResponseEntity<>(servicio, HttpStatus.OK);
	}

	// Endpoint para obtener los detalles de un servicio por su nombre
	@GetMapping("/getServiceByName/{nombre}")
	public ResponseEntity<Object> getServiceByName(@PathVariable("nombre") String nombre) {
		Optional<Servicio> optionalServicio = servicioService.getByNombre(nombre);

		if (optionalServicio.isPresent()) {
			Servicio servicio = optionalServicio.get();
			return ResponseEntity.ok(servicio);
		} else {
			return new ResponseEntity<>(new Mensaje("El servicio con nombre '" + nombre + "' no existe"),
					HttpStatus.NOT_FOUND);
		}
	}

	// Endpoint para actualizar un servicio
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateService/{idServicio}")
	public ResponseEntity<?> updateService(@PathVariable("idServicio") Long idServicio,
			@RequestBody @Valid ServicioDto servicioDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		Optional<Servicio> existingServicioOptional = servicioService.findById(idServicio);
		if (!existingServicioOptional.isPresent()) {
			return new ResponseEntity<>(new Mensaje("No existe un servicio con el ID proporcionado"),
					HttpStatus.NOT_FOUND);
		}

		Servicio existingServicio = existingServicioOptional.get();

		if (!existingServicio.getNombre().equals(servicioDto.getNombre())
				&& servicioService.existsByNombre(servicioDto.getNombre())) {
			return ResponseEntity.badRequest().body(new Mensaje("Ya existe un servicio con el mismo nombre"));
		}

		// Integrar los campos del DTO en el objeto de entidad Servicio
		existingServicio.setNombre(servicioDto.getNombre());
		existingServicio.setDescripcion(servicioDto.getDescripcion());
		existingServicio.setPrecio(servicioDto.getPrecio());
		existingServicio.setDuracion(servicioDto.getDuracion());

		servicioService.save(existingServicio);
		return ResponseEntity.ok(new Mensaje("Servicio actualizado exitosamente"));
	}

	// Endpoint para eliminar un servicio
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteService/{idServicio}")
	public ResponseEntity<Object> deleteService(@PathVariable("idServicio") Long idServicio) {
		if (!servicioService.existsById(idServicio))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		servicioService.deleteById(idServicio);
		return new ResponseEntity<>(new Mensaje("Servicio eliminado"), HttpStatus.OK);

	}
}