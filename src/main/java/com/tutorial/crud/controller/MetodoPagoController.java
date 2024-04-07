package com.tutorial.crud.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.service.MetodoPagoService;

@RestController
@RequestMapping("/metodopago")
@CrossOrigin(origins = "http://localhost:4200")
public class MetodoPagoController {

    @Autowired
    MetodoPagoService metodoPagoService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<MetodoPago>> listarMetodos() {
        List<MetodoPago> metodosPagoDto = metodoPagoService.findAll();
        return new ResponseEntity<>(metodosPagoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if(!metodoPagoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        MetodoPago metodoPago = metodoPagoService.getOne(id).get();
        MetodoPagoDto metodoPagoDto = new MetodoPagoDto();
        BeanUtils.copyProperties(metodoPago, metodoPagoDto);
        return new ResponseEntity<>(metodoPagoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid MetodoPagoDto metodoPagoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
        }
        if(metodoPagoService.existsByMetodoNombre(metodoPagoDto.getMetodoNombre()))
            return new ResponseEntity<>(new Mensaje("El método de pago ya existe"), HttpStatus.BAD_REQUEST);

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setMetodoNombre(metodoPagoDto.getMetodoNombre());
        metodoPagoService.save(metodoPago);
        return new ResponseEntity<>(new Mensaje("Método de pago creado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody @Valid MetodoPagoDto metodoPagoDto, BindingResult bindingResult){
        if(!metodoPagoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
        }
        if(metodoPagoService.existsByMetodoNombre(metodoPagoDto.getMetodoNombre()) && metodoPagoService.getByMetodoNombre(metodoPagoDto.getMetodoNombre()).get().getId() != id)
            return new ResponseEntity<>(new Mensaje("Ese método de pago ya existe"), HttpStatus.BAD_REQUEST);

        MetodoPago metodoPago = metodoPagoService.findById(id).orElse(null);
        if (metodoPago == null)
            return new ResponseEntity<>(new Mensaje("No se encontró el método de pago"), HttpStatus.NOT_FOUND);
        
        metodoPago.setMetodoNombre(metodoPagoDto.getMetodoNombre());
        metodoPagoService.save(metodoPago);
        return new ResponseEntity<>(new Mensaje("Método de pago actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!metodoPagoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        metodoPagoService.delete(id);
        return new ResponseEntity<>(new Mensaje("Método de pago eliminado"), HttpStatus.OK);
    }
}
