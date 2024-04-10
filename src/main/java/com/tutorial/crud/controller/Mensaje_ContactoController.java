package com.tutorial.crud.controller;

import com.tutorial.crud.dto.ContactoDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.Contacto;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.ContactoService;

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
public class ContactoController {

    @Autowired
    private ContactoService contactoService;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/lista")
    public ResponseEntity<List<Contacto>> listarContactos() {
        List<Contacto> contactos = contactoService.list();
        return new ResponseEntity<>(contactos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Contacto> obtenerContactoPorId(@PathVariable("id") Integer id) {
        Optional<Contacto> contactoOptional = contactoService.findById(id);
        return contactoOptional.map(contacto -> new ResponseEntity<>(contacto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crearadmin")
    public ResponseEntity<Map<String, Object>> crearContactoAdmin(@RequestBody ContactoDto contactoDto) {
        // Generar la fecha y hora actuales
        LocalDateTime fechaMensaje = LocalDateTime.now();

        Contacto nuevoContacto = new Contacto(
                contactoDto.getAsunto(),
                contactoDto.getMensaje(),
                contactoDto.getAdjunto(),
                fechaMensaje, // Utilizar la fecha y hora generadas automáticamente
                usuarioService.findById(contactoDto.getIdUsuario())
                        .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"))
        );
        // Guardar el nuevo contacto en la base de datos
        contactoService.save(nuevoContacto);

        // Obtener el nombre del usuario
        String nombreUsuario = nuevoContacto.getUsuario().getNombre();

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("asunto", nuevoContacto.getAsunto());
        respuesta.put("mensaje", nuevoContacto.getMensaje());
        respuesta.put("fechaMensaje", nuevoContacto.getFechaMensaje()); // Agregar la fecha del mensaje
        respuesta.put("usuario", nombreUsuario); // Agregar el nombre del usuario

        // Devolver la respuesta HTTP con un código de estado 201 CREATED
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }



    @PostMapping("/creausuario")
    public ResponseEntity<?> creaUsuario(@Valid @RequestBody ContactoDto contactoDto) {
        // Obtener la fecha y hora actuales
        LocalDateTime fechaMensaje = LocalDateTime.now();
        // Obtener el usuario actualmente autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
        // Crear el contacto con la fecha y hora actuales y el usuario actual
        Contacto nuevoContacto = new Contacto(
                contactoDto.getAsunto(),
                contactoDto.getMensaje(),
                contactoDto.getAdjunto(),
                fechaMensaje,
                usuario
        );
        contactoService.save(nuevoContacto);
        // Devolver un mensaje de confirmación
        return new ResponseEntity<>(new Mensaje("Mensaje de contacto guardado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarContacto(@PathVariable("id") Integer id, @Valid @RequestBody ContactoDto contactoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("Error en la validación de los campos"), HttpStatus.BAD_REQUEST);
        }

        Optional<Contacto> optionalContacto = contactoService.findById(id);
        if (!optionalContacto.isPresent()) {
            return new ResponseEntity<>(new Mensaje("El mensaje de contacto no existe"), HttpStatus.NOT_FOUND);
        }
        Contacto contacto = optionalContacto.get();
        BeanUtils.copyProperties(contactoDto, contacto, "id", "usuario");
        contactoService.save(contacto);
        return new ResponseEntity<>(contacto, HttpStatus.OK);
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Mensaje> eliminarContacto(@PathVariable("id") Integer id) {
        if (!contactoService.existsById(id)) {
            return new ResponseEntity<>(new Mensaje("El mensaje de contacto no existe"), HttpStatus.NOT_FOUND);
        }
        contactoService.delete(id);
        return new ResponseEntity<>(new Mensaje("El mensaje de contacto se ha eliminado correctamente"), HttpStatus.OK);
    }

}
