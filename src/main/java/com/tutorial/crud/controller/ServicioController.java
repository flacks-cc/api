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
@RequestMapping("/servicio")
@CrossOrigin(origins = "http://localhost:4200")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping("/lista")
    public ResponseEntity<List<Servicio>> listAllServicios() {
        List<Servicio> listaServicios = servicioService.list();
        return new ResponseEntity<>(listaServicios, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getServicioById(@PathVariable("id") int id) {
        if (!servicioService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Servicio servicio = servicioService.getOne(id).get();
        return new ResponseEntity<>(servicio, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteServicio(@PathVariable("id") int id) {
        if (!servicioService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        servicioService.delete(id);
        return new ResponseEntity<>(new Mensaje("Servicio eliminado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createServicio(@RequestBody @Valid ServicioDto servicioDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if (servicioService.existsByNombre(servicioDto.getNombre())) {
            return ResponseEntity.badRequest().body(new Mensaje("Ya existe un servicio con el mismo nombre"));
        }
        Servicio servicio = new Servicio(
                servicioDto.getNombre(),
                servicioDto.getDescripcion(),
                servicioDto.getPrecio(),
                servicioDto.getDuracion()
        );
        servicioService.save(servicio);
        return ResponseEntity.ok(new Mensaje("Servicio creado exitosamente"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateServicio(@PathVariable("id") int id, @RequestBody @Valid ServicioDto servicioDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Optional<Servicio> existingServicioOptional = servicioService.getOne(id);
        if (!existingServicioOptional.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No existe un servicio con el ID proporcionado"), HttpStatus.NOT_FOUND);
        }

        Servicio existingServicio = existingServicioOptional.get();

        if (!existingServicio.getNombre().equals(servicioDto.getNombre()) && servicioService.existsByNombre(servicioDto.getNombre())) {
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
    
    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Object> getServicioByNombre(@PathVariable("nombre") String nombre) {
        Optional<Servicio> optionalServicio = servicioService.getByNombre(nombre);
        
        if (optionalServicio.isPresent()) {
            Servicio servicio = optionalServicio.get();
            return ResponseEntity.ok(servicio);
        } else {
            return new ResponseEntity<>(new Mensaje("El servicio con nombre '" + nombre + "' no existe"), HttpStatus.NOT_FOUND);
        }
    }
}
