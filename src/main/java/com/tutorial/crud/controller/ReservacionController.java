package com.tutorial.crud.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
import com.tutorial.crud.dto.ReservacionDto;
import com.tutorial.crud.entity.Reservacion;
import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.ReservacionService;
import com.tutorial.crud.service.ServicioService;

@RestController
@Controller
@RequestMapping("/api/reservaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservacionController {

    @Autowired
    private ReservacionService reservacionService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ServicioService servicioService;
    
    public ReservacionController(ReservacionService reservacionService) {
        this.reservacionService = reservacionService;
    }
    
    @GetMapping("/lista")
    public ResponseEntity<List<Reservacion>> listAllReservaciones() {
        List<Reservacion> listaReservaciones = reservacionService.list();
        return new ResponseEntity<>(listaReservaciones, HttpStatus.OK);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Object> getReservacionById(@PathVariable("id") int id) {
        Optional<Reservacion> reservacionOptional = reservacionService.getOne(id);
        if (!reservacionOptional.isPresent())
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Reservacion reservacion = reservacionOptional.get();
        return new ResponseEntity<>(reservacion, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> deleteReservacion(@PathVariable("id") int id) {
        if (!reservacionService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        reservacionService.delete(id);
        return new ResponseEntity<>(new Mensaje("Reservación eliminada"), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crearusuario")
    public ResponseEntity<?> crearReservacion(@RequestBody ReservacionDto reservacionDto) {
        // Obtener el nombre de usuario del token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();

        // Obtener el usuario a partir del nombre de usuario
        Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        // Obtener el servicio a partir del ID proporcionado
        Servicio servicio = servicioService.findById(reservacionDto.getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("El servicio especificado no existe"));

        // Calcular la hora de fin de la reservación
        LocalTime horaInicio = reservacionDto.getHoraInicio();
        int duracionEnMinutos = servicio.getDuracion(); // Obtener la duración del servicio en minutos
        Duration duracion = Duration.ofMinutes(duracionEnMinutos); // Convertir la duración a Duration
        LocalTime horaFin = horaInicio.plus(duracion);

        // Verificar si existe alguna reservación en el intervalo de tiempo deseado
        if (reservacionService.existeReservaEnIntervalo(horaInicio, horaFin)) {
            return ResponseEntity.badRequest().body(new Mensaje("Ya existe una reservación en este horario"));
        }

        // Crear la reservación
        Reservacion reservacion = new Reservacion(reservacionDto.getFechaReserva(), horaInicio, horaFin, servicio, usuario);

        // Guardar la reservación en la base de datos
        reservacionService.save(reservacion);

        // Devolver una respuesta exitosa
        return ResponseEntity.ok(new Mensaje("Reservación creada exitosamente"));
    }
   
    @PostMapping("/crearadmin")
    public ResponseEntity<?> create(@RequestBody @Valid ReservacionDto reservacionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
        }

        // Obtener el servicio a partir del ID proporcionado
        Optional<Servicio> servicioOptional = servicioService.findById(reservacionDto.getIdServicio());
        if (!servicioOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new Mensaje("El servicio especificado no existe"));
        }

        // Obtener el usuario a partir del ID proporcionado
        Optional<Usuario> usuarioOptional = usuarioService.findById(reservacionDto.getIdUsuario());
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new Mensaje("El usuario especificado no existe"));
        }

        // Calcular la hora de fin de la reservación
        LocalTime horaInicio = reservacionDto.getHoraInicio();
        int duracionEnMinutos = servicioOptional.get().getDuracion(); // Obtener la duración del servicio en minutos
        LocalTime horaFin = horaInicio.plusMinutes(duracionEnMinutos);

        // Verificar si existe alguna reservación en el intervalo de tiempo deseado
        if (reservacionService.existeReservaEnIntervalo(horaInicio, horaFin)) {
            return ResponseEntity.badRequest().body(new Mensaje("Ya existe una reservación en este horario"));
        }

        // Crear la reservación
        Reservacion reservacion = new Reservacion(
                reservacionDto.getFechaReserva(),
                horaInicio,
                horaFin,
                servicioOptional.get(),
                usuarioOptional.get()
        );
        
        // Guardar la reservación en la base de datos
        reservacionService.save(reservacion);
        return ResponseEntity.ok(new Mensaje("Reservación creada exitosamente"));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateReservacion(@PathVariable("id") int id, @RequestBody @Valid ReservacionDto reservacionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
        }

        Optional<Reservacion> reservacionOptional = reservacionService.getOne(id);
        if (!reservacionOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new Mensaje("La reservación no existe"));
        }

        // Obtener el servicio a partir del ID proporcionado
        Optional<Servicio> servicioOptional = servicioService.findById(reservacionDto.getIdServicio());
        if (!servicioOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new Mensaje("El servicio especificado no existe"));
        }

        // Obtener el usuario a partir del ID proporcionado
        Optional<Usuario> usuarioOptional = usuarioService.findById(reservacionDto.getIdUsuario());
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new Mensaje("El usuario especificado no existe"));
        }

        // Calcular la hora de fin de la reservación
        LocalTime horaInicio = reservacionDto.getHoraInicio();
        int duracionEnMinutos = servicioOptional.get().getDuracion(); // Obtener la duración del servicio en minutos
        LocalTime horaFin = horaInicio.plusMinutes(duracionEnMinutos);

        // Verificar si existe alguna otra reservación en el intervalo de tiempo deseado
        if (reservacionService.existeReservaEnIntervalo(horaInicio, horaFin)) {
            return ResponseEntity.badRequest().body(new Mensaje("Ya existe otra reservación en este horario"));
        }

        // Actualizar la reservación existente con los nuevos datos
        Reservacion reservacion = reservacionOptional.get();
        reservacion.setFechaReserva(reservacionDto.getFechaReserva());
        reservacion.setHoraInicio(horaInicio);
        reservacion.setHoraFin(horaFin);
        reservacion.setServicio(servicioOptional.get());
        reservacion.setUsuario(usuarioOptional.get());

        // Guardar la reservación actualizada en la base de datos
        reservacionService.save(reservacion);
        return ResponseEntity.ok(new Mensaje("Reservación actualizada exitosamente"));
    }
}
