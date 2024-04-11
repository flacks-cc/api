package com.tutorial.crud.controller;

import com.tutorial.crud.dto.MensajeContactoDto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController

//Cambia el nombre de las rutas por '/api/(controlador)'
@RequestMapping("/api/mensajeContacto")
@CrossOrigin(origins = "http://localhost:4200")
public class MensajeContactoController {

	@Autowired
	private MensajeContactoService mensajeContactoService;

	@Autowired
	private UsuarioService usuarioService;

	// RECUERDA CAMBIAR EL NOMBRE DE LAS VARIABLES Y LOS OBJETOS AL DE LA CLASE A LA
	// QUE HACEN REFERENCIA.
	// ESO HACE QUE EL CÓDIGO SEA MÁS LEGIBLE Y FÁCIL DE ENTENDER PARA NOSOTROS.
	// ABAJO TE DEJO EL NOMBRE QUE DEBEN DE LLEVAR LOS ENDPOINTS.
	// SUS MÉTODOS DEBEN DE LLEVAR EL MISMO NOMBRE QUE LOS ENDPOINTS.
	// SE ENCUENTRAN LAS 4 OPERACIONES CRUD, ENTONCES QUEDA A TU CONSIDERACIÓN
	// AÑADIR O QUITAR MÁS.
	// ORGANIZA LOS ENDPOINTS CON BASE A LAS SIGLAS 'CRUD': POST, GET, PUT, DELETE.

	// El NOMBRE DE LOS ENDPOINTS DEBE DE SER EN INGLÉS
	@PostMapping("/createMessage")
	public ResponseEntity<?> createMessage(@Valid @RequestBody MensajeContactoDto mensajeContactoDto) {
		// Obtener la fecha y hora actuales
		LocalDateTime fechaHora = LocalDateTime.now();
		// Obtener el usuario actualmente autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = authentication.getName();
		Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
		// Crear el contacto con la fecha y hora actuales y el usuario actual
		MensajeContacto nuevoContacto = new MensajeContacto(mensajeContactoDto.getAsunto(),
				mensajeContactoDto.getMensaje(), mensajeContactoDto.getAdjunto(), fechaHora, usuario);
		mensajeContactoService.save(nuevoContacto);
		// Devolver un mensaje de confirmación
		return new ResponseEntity<>(new Mensaje("Mensaje de contacto guardado exitosamente"), HttpStatus.OK);
	}

	// Endpoint para obtener la lista de todos los Mensajes
	@GetMapping("/getAllMessages")
	public ResponseEntity<List<Map<String, Object>>> getAllMessages() {
	    List<MensajeContacto> mensajes = mensajeContactoService.findAll();

	    List<Map<String, Object>> mensajesMap = new ArrayList<>();
	    for (MensajeContacto mensaje : mensajes) {
	        Map<String, Object> mensajeMap = new HashMap<>();
	        mensajeMap.put("idMensaje", mensaje.getIdMensaje());
	        mensajeMap.put("asunto", mensaje.getAsunto());
	        mensajeMap.put("mensaje", mensaje.getMensaje());
	        mensajeMap.put("adjunto", mensaje.getAdjunto());
	        mensajeMap.put("fechaHora", mensaje.getFechaHora());
	        mensajeMap.put("nombreCliente", mensaje.getCliente().getNombre()); // Obtener solo el nombre del cliente
	        mensajesMap.add(mensajeMap);
	    }

	    return new ResponseEntity<>(mensajesMap, HttpStatus.OK);
	}

	// Sí se puede añadir más de un rol. La sintáxis es la siguiente:
	// @PreAuthorize("hasRole('USER') and hasRole('ADMIN')")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getMessageById/{idMensaje}")
	public ResponseEntity<MensajeContacto> getMessageById(@PathVariable("idMensaje") Long idMensaje) {
		Optional<MensajeContacto> contactoOptional = mensajeContactoService.findById(idMensaje);
		return contactoOptional.map(contacto -> new ResponseEntity<>(contacto, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateMessage/{idMensaje}")
	public ResponseEntity<?> updateMessage(@PathVariable("idMensaje") Long idMensaje,
	                                         @Valid @RequestBody MensajeContactoDto mensajeContactoDto,
	                                         BindingResult bindingResult) {

	    // 1. Validación de datos del mensaje de contacto
	    if (bindingResult.hasErrors()) {
	        return new ResponseEntity<>(new Mensaje("Error en la validación de los campos"), HttpStatus.BAD_REQUEST);
	    }
	    // 2. Búsqueda del mensaje de contacto por ID
	    Optional<MensajeContacto> mensajeContactoOptional = mensajeContactoService.findById(idMensaje);
	    if (!mensajeContactoOptional.isPresent()) {
	        return new ResponseEntity<>(new Mensaje("El mensaje de contacto no existe"), HttpStatus.NOT_FOUND);
	    }
	    // 3. Obtención del mensaje de contacto existente
	    MensajeContacto mensajeContacto = mensajeContactoOptional.get();
	    // 4. Actualización de los datos del mensaje de contacto
	    mensajeContacto.setAsunto(mensajeContactoDto.getAsunto());
	    mensajeContacto.setMensaje(mensajeContactoDto.getMensaje());
	    mensajeContacto.setAdjunto(mensajeContactoDto.getAdjunto());
	    // 5. Establecer la fecha y hora actualizadas automáticamente
	    mensajeContacto.setFechaHora(LocalDateTime.now());
	    // 6. Guardado del mensaje de contacto actualizado
	    mensajeContactoService.save(mensajeContacto);
	    // 7. Retorno de una respuesta de éxito
	    return new ResponseEntity<>(mensajeContacto, HttpStatus.OK);
	}


	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteMessage/{idMensaje}")
	public ResponseEntity<Mensaje> deleteMessage(@PathVariable("idMensaje") Long idMensaje) {
		if (!mensajeContactoService.existsById(idMensaje)) {
			return new ResponseEntity<>(new Mensaje("El mensaje de contacto no existe"), HttpStatus.NOT_FOUND);
		}
		mensajeContactoService.deleteById(idMensaje);
		return new ResponseEntity<>(new Mensaje("El mensaje de contacto se ha eliminado correctamente"), HttpStatus.OK);
	}
}
