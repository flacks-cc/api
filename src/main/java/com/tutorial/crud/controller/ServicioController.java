package com.tutorial.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createService")
	public ResponseEntity<?> createService(@RequestBody @Valid ServicioDto servicioDto, BindingResult bindingResult) {
	    // 1. Validación de datos del servicio
	    if (bindingResult.hasErrors()) {
	        List<String> errors = bindingResult.getAllErrors().stream()
	                .map(ObjectError::getDefaultMessage)
	                .collect(Collectors.toList());
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
	    // 2. Verificación de existencia por nombre (Opcional)
	    if (servicioService.existsByNombre(servicioDto.getNombre())) {
	        return ResponseEntity.badRequest().body(new Mensaje("Ya existe un servicio con el mismo nombre"));
	    }
	    // 3. Creación del objeto Servicio a partir del DTO
	    Servicio servicio = new Servicio(
	            servicioDto.getNombre(),
	            servicioDto.getDescripcion(),
	            servicioDto.getPrecio(),
	            servicioDto.getDuracion()
	    );
	    // 4. Guardado del servicio en la base de datos
	    servicioService.save(servicio);
	    // 5. Retorno de una respuesta exitosa
	    return ResponseEntity.ok(new Mensaje("Servicio creado exitosamente"));
	}

	@GetMapping("/getAllServices")
	public ResponseEntity<List<Map<String, Object>>> getAllServices() {
	    // Obtener todas los servicios de la base de datos
	    List<Servicio> listaServicios = servicioService.findAll();
	    
	    // Convertir los servicios a una lista de mapas con todos los campos
	    List<Map<String, Object>> serviciosMap = new ArrayList<>();
	    for (Servicio servicio : listaServicios) {
	        Map<String, Object> servicioMap = new HashMap<>();
	        servicioMap.put("idServicio", servicio.getIdServicio());
	        servicioMap.put("nombre", servicio.getNombre());
	        servicioMap.put("descripcion", servicio.getDescripcion());
	        servicioMap.put("precio", servicio.getPrecio());
	        servicioMap.put("duracion", servicio.getDuracion());
	        serviciosMap.add(servicioMap);
	    }

	    return new ResponseEntity<>(serviciosMap, HttpStatus.OK);
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
	                                         @RequestBody @Valid ServicioDto servicioDto,
	                                         BindingResult bindingResult) {
	    // 1. Validación de datos del servicio
	    if (bindingResult.hasErrors()) {
	        List<String> errors = bindingResult.getAllErrors().stream()
	                .map(ObjectError::getDefaultMessage)
	                .collect(Collectors.toList());
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
	    // 2. Búsqueda del servicio por su ID
	    Optional<Servicio> existingServicioOptional = servicioService.findById(idServicio);
	    // 3. Verificación de existencia del servicio
	    if (!existingServicioOptional.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }
	    // 4. Obtención del objeto Servicio existente
	    Servicio existingServicio = existingServicioOptional.get();
	    // 5. Verificación de cambio de nombre y existencia por nuevo nombre (Opcional)
	    if (!existingServicio.getNombre().equals(servicioDto.getNombre()) && servicioService.existsByNombre(servicioDto.getNombre())) {
	        return ResponseEntity.badRequest().body(new Mensaje("Ya existe un servicio con el mismo nombre"));
	    }
	    // 6. Actualización de los campos del servicio con los datos del DTO
	    existingServicio.setNombre(servicioDto.getNombre());
	    existingServicio.setDescripcion(servicioDto.getDescripcion());
	    existingServicio.setPrecio(servicioDto.getPrecio());
	    existingServicio.setDuracion(servicioDto.getDuracion());
	    // 7. Guardado del servicio actualizado en la base de datos
	    servicioService.save(existingServicio);
	    // 8. Retorno de una respuesta exitosa
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