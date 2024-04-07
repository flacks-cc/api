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
import com.tutorial.crud.dto.TarjetaDto;
import com.tutorial.crud.entity.Tarjeta;
import com.tutorial.crud.service.TarjetaService;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;

@RestController
@RequestMapping("/tarjeta")
@CrossOrigin(origins = "http://localhost:4200")
public class TarjetaController {

    @Autowired
    TarjetaService tarjetaService;

    @Autowired
    UsuarioService usuarioService;

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<TarjetaDto>> list() {
        List<Tarjeta> tarjetas = tarjetaService.list();
        List<TarjetaDto> tarjetasDto = tarjetas.stream().map(tarjeta -> {
            TarjetaDto tarjetaDto = new TarjetaDto();
            BeanUtils.copyProperties(tarjeta, tarjetaDto);
            // Asignar el ID de usuario al DTO
            tarjetaDto.setIdUsuario(tarjeta.getUsuario().getId());
            return tarjetaDto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(tarjetasDto, HttpStatus.OK);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        if (!tarjetaService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Tarjeta tarjeta = tarjetaService.getOne(id).get();
        TarjetaDto tarjetaDto = new TarjetaDto();
        tarjetaDto.setId(tarjeta.getId()); 
        tarjetaDto.setUid(tarjeta.getUid());
        tarjetaDto.setIdUsuario(tarjeta.getUsuario().getId()); 
        return new ResponseEntity<>(tarjetaDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody @Valid TarjetaDto tarjetaDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
        }
        if(tarjetaService.existsByUid(tarjetaDto.getUid()))
            return new ResponseEntity<>(new Mensaje("La tarjeta ya existe"), HttpStatus.BAD_REQUEST);

        Usuario usuario = usuarioService.findById(tarjetaDto.getIdUsuario()).orElse(null);
        if (usuario == null)
            return new ResponseEntity<>(new Mensaje("El usuario asociado no existe"), HttpStatus.BAD_REQUEST);

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setUid(tarjetaDto.getUid());
        tarjeta.setUsuario(usuario);
        tarjetaService.save(tarjeta);
        return new ResponseEntity<>(new Mensaje("Tarjeta creada exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody @Valid TarjetaDto tarjetaDto, BindingResult bindingResult){
        if(!tarjetaService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
        }
        if(tarjetaService.existsByUid(tarjetaDto.getUid()) && tarjetaService.getByUid(tarjetaDto.getUid()).get().getId() != id)
            return new ResponseEntity<>(new Mensaje("Esa tarjeta ya existe"), HttpStatus.BAD_REQUEST);

        Tarjeta tarjeta = tarjetaService.findById(id).orElse(null);
        if (tarjeta == null)
            return new ResponseEntity<>(new Mensaje("No se encontró la tarjeta"), HttpStatus.NOT_FOUND);
        
        Usuario usuario = usuarioService.findById(tarjetaDto.getIdUsuario()).orElse(null);
        if (usuario == null)
            return new ResponseEntity<>(new Mensaje("El usuario asociado no existe"), HttpStatus.BAD_REQUEST);

        tarjeta.setUid(tarjetaDto.getUid());
        tarjeta.setUsuario(usuario);
        tarjetaService.save(tarjeta);
        return new ResponseEntity<>(new Mensaje("Tarjeta actualizada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!tarjetaService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        tarjetaService.delete(id);
        return new ResponseEntity<>(new Mensaje("Tarjeta eliminada"), HttpStatus.OK);
    }
}
