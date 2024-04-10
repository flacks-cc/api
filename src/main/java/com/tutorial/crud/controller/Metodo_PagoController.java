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
import com.tutorial.crud.dto.Metodos_PagoDto;
import com.tutorial.crud.entity.Metodo_Pago;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.service.Metodo_PagoService;

@RestController
@RequestMapping("/api/metodoPago")
@CrossOrigin(origins = "http://localhost:4200")
public class Metodo_PagoController {

    @Autowired
    Metodo_PagoService metodo_PagoService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<Metodo_Pago>> listarMetodos() {
        List<Metodo_Pago> metodosPagoDto = metodo_PagoService.findAll();
        return new ResponseEntity<>(metodosPagoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if(!metodo_PagoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Metodo_Pago metodoPago = metodo_PagoService.getOne(id).get();
        Metodos_PagoDto metodos_PagoDto = new Metodos_PagoDto();
        BeanUtils.copyProperties(metodoPago, metodos_PagoDto);
        return new ResponseEntity<>(metodos_PagoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid Metodos_PagoDto metodos_PagoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
        }
        if(metodo_PagoService.existsByMetodoNombre(metodos_PagoDto.getMetodoNombre()))
            return new ResponseEntity<>(new Mensaje("El método de pago ya existe"), HttpStatus.BAD_REQUEST);

        Metodo_Pago metodoPago = new Metodo_Pago();
        metodoPago.setMetodoNombre(metodos_PagoDto.getMetodoNombre());
        metodo_PagoService.save(metodoPago);
        return new ResponseEntity<>(new Mensaje("Método de pago creado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody @Valid Metodos_PagoDto metodos_PagoDto, BindingResult bindingResult){
        if(!metodo_PagoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
        }
        if(metodo_PagoService.existsByMetodoNombre(metodos_PagoDto.getMetodoNombre()) && metodo_PagoService.getByMetodoNombre(metodos_PagoDto.getMetodoNombre()).get().getId() != id)
            return new ResponseEntity<>(new Mensaje("Ese método de pago ya existe"), HttpStatus.BAD_REQUEST);

        Metodo_Pago metodoPago = metodo_PagoService.findById(id).orElse(null);
        if (metodoPago == null)
            return new ResponseEntity<>(new Mensaje("No se encontró el método de pago"), HttpStatus.NOT_FOUND);
        
        metodoPago.setMetodoNombre(metodos_PagoDto.getMetodoNombre());
        metodo_PagoService.save(metodoPago);
        return new ResponseEntity<>(new Mensaje("Método de pago actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!metodo_PagoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        metodo_PagoService.delete(id);
        return new ResponseEntity<>(new Mensaje("Método de pago eliminado"), HttpStatus.OK);
    }
}
