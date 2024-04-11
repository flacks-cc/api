package com.tutorial.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
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
import com.tutorial.crud.dto.MetodoPagoDto;
import com.tutorial.crud.entity.MetodoPago;
import com.tutorial.crud.service.MetodoPagoService;

@RestController
@RequestMapping("/api/metodoPago")
@CrossOrigin(origins = "http://localhost:4200")
public class MetodoPagoController {

	@Autowired
	MetodoPagoService metodoPagoService;

	// Endpoint para crear un nuevo método de pago (Solo ADMIN)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createPaymentMethod")
	public ResponseEntity<?> createPaymentMethod(@RequestBody @Valid MetodoPagoDto metodoPagoDto,
	                                                 BindingResult bindingResult) {
	    // 1. Validación de datos del método de pago
	    if (bindingResult.hasErrors()) {
	        List<ObjectError> errors = bindingResult.getAllErrors();
	        StringBuilder errorMessage = new StringBuilder();
	        for (ObjectError error : errors) {
	            errorMessage.append(error.getDefaultMessage()).append(". ");
	        }
	        return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
	    }
	    // 2. Verificación de existencia por nombre (Opcional)
	    if (metodoPagoService.existsByNombre(metodoPagoDto.getNombre())) {
	        return new ResponseEntity<>(new Mensaje("El método de pago ya existe"), HttpStatus.BAD_REQUEST);
	    }
	    // 3. Creación del objeto MétodoPago a partir del DTO
	    MetodoPago metodoPago = new MetodoPago();
	    metodoPago.setNombre(metodoPagoDto.getNombre()); // Asignar el nombre desde el DTO
	    // 4. Guardado del método de pago en la base de datos
	    metodoPagoService.save(metodoPago);
	    // 5. Retorno de una respuesta exitosa
	    return new ResponseEntity<>(new Mensaje("Método de pago creado exitosamente"), HttpStatus.OK);
	}
	
	
	// Endpoint para obtener la lista de todos los Metodos de pago
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllPaymentMethods")
	public ResponseEntity<List<Map<String, Object>>> getAllPaymentMethods() {
	    List<MetodoPago> paymentMethods = metodoPagoService.findAll();
	    List<Map<String, Object>> paymentMethodMaps = new ArrayList<>();
	    for (MetodoPago paymentMethod : paymentMethods) {
	        Map<String, Object> paymentMethodMap = new HashMap<>();
	        paymentMethodMap.put("idMetodoPago", paymentMethod.getIdMetodoPago());
	        paymentMethodMap.put("nombre", paymentMethod.getNombre());
	        paymentMethodMaps.add(paymentMethodMap);
	    }
	    return new ResponseEntity<>(paymentMethodMaps, HttpStatus.OK);
	}


	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getPaymentMethodById/{idMetodoPago}")
	public ResponseEntity<?> getPaymentMethodById(@PathVariable("idMetodoPago") Long idMetodoPago) {
	    // 1. Buscar el método de pago por su ID
	    Optional<MetodoPago> metodoPago = metodoPagoService.findById(idMetodoPago);
	    // 2. Verificar si el método de pago existe
	    if (!metodoPago.isPresent()) {
	        // Método de pago no encontrado
	        return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
	    }
	    // 3. Convertir la entidad a DTO
	    MetodoPagoDto metodoPagoDto = new MetodoPagoDto();
	    BeanUtils.copyProperties(metodoPago.get(), metodoPagoDto);  // Copiar los valores de la entidad al DTO
	    // 4. Retornar el DTO del método de pago
	    return new ResponseEntity<>(metodoPagoDto, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updatePaymentMethod/{idMetodoPago}")
	public ResponseEntity<?> updatePaymentMethod(@PathVariable("idMetodoPago") Long idMetodoPago,
	                                                 @RequestBody @Valid MetodoPagoDto metodoPagoDto,
	                                                 BindingResult bindingResult) {

	    // 1. Validación de datos del método de pago
	    if (bindingResult.hasErrors()) {
	        List<ObjectError> errors = bindingResult.getAllErrors();
	        StringBuilder errorMessage = new StringBuilder();
	        for (ObjectError error : errors) {
	            errorMessage.append(error.getDefaultMessage()).append(". ");
	        }
	        return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
	    }
	    // 2. Verificación de existencia del método de pago
	    if (!metodoPagoService.existsById(idMetodoPago)) {
	        // Método de pago no encontrado
	        return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
	    }
	    // 3. Verificación de existencia por nombre (Opcional)
	    if (metodoPagoService.existsByNombre(metodoPagoDto.getNombre()) &&
	            metodoPagoService.getByNombre(metodoPagoDto.getNombre()).get().getIdMetodoPago() != idMetodoPago) {
	        // Ese método de pago ya existe
	        return new ResponseEntity<>(new Mensaje("Ese método de pago ya existe"), HttpStatus.BAD_REQUEST);
	    }
	    // 4. Obtención del método de pago existente
	    MetodoPago metodoPago = metodoPagoService.findById(idMetodoPago).get();
	    // 5. Actualización de los datos del método de pago
	    metodoPago.setNombre(metodoPagoDto.getNombre()); // Actualizar el nombre
	    // 6. Guardado del método de pago actualizado
	    metodoPagoService.save(metodoPago);
	    // 7. Retorno de una respuesta de éxito
	    return new ResponseEntity<>(new Mensaje("Método de pago actualizado"), HttpStatus.OK);
	}

	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletePaymentMethod/{idMetodoPago}")
	public ResponseEntity<?> deletePaymentMethod(@PathVariable("idMetodoPago") Long idMetodoPago) {
	    // 1. Verificar si el método de pago existe
	    if (!metodoPagoService.existsById(idMetodoPago)) {
	        // Método de pago no encontrado
	        return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
	    }
	    // 2. Eliminar el método de pago
	    metodoPagoService.deleteById(idMetodoPago);
	    // 3. Retornar una respuesta de éxito
	    return new ResponseEntity<>(new Mensaje("Método de pago eliminado"), HttpStatus.OK);
	}

}
