package com.tutorial.crud.controller;

import java.util.List;
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

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createPaymentMethod")
	public ResponseEntity<?> createPaymentMethod(@RequestBody @Valid MetodoPagoDto metodoPagoDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			StringBuilder errorMessage = new StringBuilder();
			for (ObjectError error : errors) {
				errorMessage.append(error.getDefaultMessage()).append(". ");
			}
			return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
		}
		if (metodoPagoService.existsByNombre(metodoPagoDto.getNombre()))
			return new ResponseEntity<>(new Mensaje("El método de pago ya existe"), HttpStatus.BAD_REQUEST);

		MetodoPago metodoPago = new MetodoPago();
		metodoPago.setNombre(metodoPagoDto.getNombre());
		metodoPagoService.save(metodoPago);
		return new ResponseEntity<>(new Mensaje("Método de pago creado exitosamente"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllPaymentMethods")
	public ResponseEntity<List<MetodoPago>> getAllPaymentMethods() {
		List<MetodoPago> metodosPagoDto = metodoPagoService.findAll();
		return new ResponseEntity<>(metodosPagoDto, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getPaymentMethodById/{idMetodoPago}")
	public ResponseEntity<?> getPaymentMethodById(@PathVariable("idMetodoPago") Long idMetodoPago) {
		if (!metodoPagoService.existsById(idMetodoPago))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		MetodoPago metodoPago = metodoPagoService.findById(idMetodoPago).get();
		MetodoPagoDto metodoPagoDto = new MetodoPagoDto();
		BeanUtils.copyProperties(metodoPago, metodoPagoDto);
		return new ResponseEntity<>(metodoPagoDto, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updatePaymentMethod/{idMetodoPago}")
	public ResponseEntity<?> updatePaymentMethod(@PathVariable("idMetodoPago") Long idMetodoPago,
			@RequestBody @Valid MetodoPagoDto metodoPagoDto, BindingResult bindingResult) {
		if (!metodoPagoService.existsById(idMetodoPago))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			StringBuilder errorMessage = new StringBuilder();
			for (ObjectError error : errors) {
				errorMessage.append(error.getDefaultMessage()).append(". ");
			}
			return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
		}
		if (metodoPagoService.existsByNombre(metodoPagoDto.getNombre()) && metodoPagoService
				.getByNombre(metodoPagoDto.getNombre()).get().getIdMetodoPago() != idMetodoPago)
			return new ResponseEntity<>(new Mensaje("Ese método de pago ya existe"), HttpStatus.BAD_REQUEST);

		MetodoPago metodoPago = metodoPagoService.findById(idMetodoPago).orElse(null);
		if (metodoPago == null)
			return new ResponseEntity<>(new Mensaje("No se encontró el método de pago"), HttpStatus.NOT_FOUND);

		metodoPago.setNombre(metodoPagoDto.getNombre());
		metodoPagoService.save(metodoPago);
		return new ResponseEntity<>(new Mensaje("Método de pago actualizado"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deletePaymentMethod/{idMetodoPago}")
	public ResponseEntity<?> deletePaymentMethod(@PathVariable("idMetodoPago") Long idMetodoPago) {
		if (!metodoPagoService.existsById(idMetodoPago))
			return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
		metodoPagoService.deleteById(idMetodoPago);
		return new ResponseEntity<>(new Mensaje("Método de pago eliminado"), HttpStatus.OK);
	}
}
