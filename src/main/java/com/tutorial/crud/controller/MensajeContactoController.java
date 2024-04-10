package com.tutorial.crud.controller;

import com.tutorial.crud.dto.ContactoDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.MensajeContacto;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.MensajeContactoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/contactos")
@CrossOrigin(origins = "http://localhost:4200")
public class MensajeContactoController {

	@Autowired
	private MensajeContactoService mensajeContactoService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/lista")
	public ResponseEntity<List<MensajeContacto>> listarContactos() {
		List<MensajeContacto> contactos = mensajeContactoService.list();
		return new ResponseEntity<>(contactos, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/detalle/{id}")
	public ResponseEntity<MensajeContacto> obtenerContactoPorId(@PathVariable("id") Integer id) {
		Optional<MensajeContacto> contactoOptional = mensajeContactoService.findById(id);
		return contactoOptional.map(mensajeContacto -> new ResponseEntity<>(mensajeContacto, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/crearadmin")
	public ResponseEntity<Map<String, Object>> crearContactoAdmin(@RequestBody ContactoDto contactoDto) {
		// Generar la fecha y hora actuales
		LocalDateTime fechaHora = LocalDateTime.now();

		MensajeContacto nuevoContacto = new MensajeContacto();
		// Guardar el nuevo mensajeContacto en la base de datos
		mensajeContactoService.save(nuevoContacto);

		// Obtener el nombre del usuario
		String nombreUsuario = nuevoContacto.getCliente().getNombre();

		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("asunto", nuevoContacto.getAsunto());
		respuesta.put("mensaje", nuevoContacto.getMensaje());
		respuesta.put("fechaHora", nuevoContacto.getFechaHora()); // Agregar la fecha del mensaje
		respuesta.put("usuario", nombreUsuario); // Agregar el nombre del usuario

		// Devolver la respuesta HTTP con un código de estado 201 CREATED
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}

	@PostMapping("/creausuario")
	public ResponseEntity<?> creaUsuario(@Valid @RequestBody ContactoDto contactoDto) {
		// Obtener la fecha y hora actuales
		LocalDateTime fechaHora = LocalDateTime.now();
		// Obtener el usuario actualmente autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = authentication.getName();
		Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
		// Crear el mensajeContacto con la fecha y hora actuales y el usuario actual
		MensajeContacto nuevoContacto = new MensajeContacto();
		mensajeContactoService.save(nuevoContacto);
		// Devolver un mensaje de confirmación
		return new ResponseEntity<>(new Mensaje("Mensaje de mensajeContacto guardado exitosamente"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> actualizarContacto(@PathVariable("id") Integer id,
			@Valid @RequestBody ContactoDto contactoDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Mensaje("Error en la validación de los campos"), HttpStatus.BAD_REQUEST);
		}

		Optional<MensajeContacto> optionalContacto = mensajeContactoService.findById(id);
		if (!optionalContacto.isPresent()) {
			return new ResponseEntity<>(new Mensaje("El mensaje de mensajeContacto no existe"), HttpStatus.NOT_FOUND);
		}
		MensajeContacto mensajeContacto = optionalContacto.get();
		BeanUtils.copyProperties(contactoDto, mensajeContacto, "id", "usuario");
		mensajeContactoService.save(mensajeContacto);
		return new ResponseEntity<>(mensajeContacto, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Mensaje> eliminarContacto(@PathVariable("id") Integer id) {
		if (!mensajeContactoService.existsById(id)) {
			return new ResponseEntity<>(new Mensaje("El mensaje de mensajeContacto no existe"), HttpStatus.NOT_FOUND);
		}
		mensajeContactoService.delete(id);
		return new ResponseEntity<>(new Mensaje("El mensaje de mensajeContacto se ha eliminado correctamente"),
				HttpStatus.OK);
	}
}
