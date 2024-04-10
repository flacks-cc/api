package com.tutorial.crud.controller;

import com.tutorial.crud.dto.Mensajes_ContactoDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.Mensaje_Contacto;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.Mensaje_ContactoService;

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

//Cambia el nombre de las rutas por '/api/(controlador)'
@RequestMapping("/api/mensajeContacto")
@CrossOrigin(origins = "http://localhost:4200")
public class Mensaje_ContactoController {

    @Autowired
    private Mensaje_ContactoService mensaje_ContactoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    
    //RECUERDA CAMBIAR EL NOMBRE DE LAS VARIABLES Y LOS OBJETOS AL DE LA CLASE A LA QUE HACEN REFERENCIA.
    //ESO HACE QUE EL CÓDIGO SEA MÁS LEGIBLE Y FÁCIL DE ENTENDER PARA NOSOTROS.
    //ABAJO TE DEJO EL NOMBRE QUE DEBEN DE LLEVAR LOS ENDPOINTS. 
    //SUS MÉTODOS DEBEN DE LLEVAR EL MISMO NOMBRE QUE LOS ENDPOINTS.
    //SE ENCUENTRAN LAS 4 OPERACIONES CRUD, ENTONCES QUEDA A TU CONSIDERACIÓN AÑADIR O QUITAR MÁS.
    //ORGANIZA LOS ENDPOINTS CON BASE A LAS SIGLAS 'CRUD': POST, GET, PUT, DELETE.

    //El NOMBRE DE LOS ENDPOINTS DEBE DE SER EN INGLÉS
    @PostMapping("/createMessage")
    public ResponseEntity<?> createMessage(@Valid @RequestBody Mensajes_ContactoDto mensajes_ContactoDto) {
        // Obtener la fecha y hora actuales
        LocalDateTime fechaMensaje = LocalDateTime.now();
        // Obtener el usuario actualmente autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
        // Crear el contacto con la fecha y hora actuales y el usuario actual
        Mensaje_Contacto nuevoContacto = new Mensaje_Contacto(
                mensajes_ContactoDto.getAsunto(),
                mensajes_ContactoDto.getMensaje(),
                mensajes_ContactoDto.getAdjunto(),
                fechaMensaje,
                usuario
        );
        mensaje_ContactoService.save(nuevoContacto);
        // Devolver un mensaje de confirmación
        return new ResponseEntity<>(new Mensaje("Mensaje de contacto guardado exitosamente"), HttpStatus.OK);
    }

    
    
    @GetMapping("/getAllMessages")
    public ResponseEntity<List<Mensaje_Contacto>> getAllMessages() {
        List<Mensaje_Contacto> contactos = mensaje_ContactoService.list();
        return new ResponseEntity<>(contactos, HttpStatus.OK);
    }

    
    //Sí se puede añadir más de un rol. La sintáxis es la siguiente:
    //@PreAuthorize("hasRole('USER') and hasRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getMessageById/{idMensaje}")
    public ResponseEntity<Mensaje_Contacto> getMessageById(@PathVariable("idMensaje") Integer idMensaje) {
        Optional<Mensaje_Contacto> contactoOptional = mensaje_ContactoService.findById(idMensaje);
        return contactoOptional.map(contacto -> new ResponseEntity<>(contacto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateMessage/{idMensaje}")
    public ResponseEntity<?> updateMessage(@PathVariable("idMensaje") Integer idMensaje, @Valid @RequestBody Mensajes_ContactoDto mensajes_ContactoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("Error en la validación de los campos"), HttpStatus.BAD_REQUEST);
        }

        Optional<Mensaje_Contacto> optionalContacto = mensaje_ContactoService.findById(idMensaje);
        if (!optionalContacto.isPresent()) {
            return new ResponseEntity<>(new Mensaje("El mensaje de contacto no existe"), HttpStatus.NOT_FOUND);
        }
        Mensaje_Contacto contacto = optionalContacto.get();
        BeanUtils.copyProperties(mensajes_ContactoDto, contacto, "idMensaje", "usuario");
        mensaje_ContactoService.save(contacto);
        return new ResponseEntity<>(contacto, HttpStatus.OK);
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteMessage/{idMensaje}")
    public ResponseEntity<Mensaje> deleteMessage(@PathVariable("idMensaje") Integer idMensaje) {
        if (!mensaje_ContactoService.existsById(idMensaje)) {
            return new ResponseEntity<>(new Mensaje("El mensaje de contacto no existe"), HttpStatus.NOT_FOUND);
        }
        mensaje_ContactoService.delete(idMensaje);
        return new ResponseEntity<>(new Mensaje("El mensaje de contacto se ha eliminado correctamente"), HttpStatus.OK);
    }

}
