package com.tutorial.crud.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.tutorial.crud.dto.ResenaDto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Resena;
import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.ProductoService;
import com.tutorial.crud.service.ResenaService;
import com.tutorial.crud.service.ServicioService;

@RestController
@RequestMapping("/resenas")
@CrossOrigin(origins = "http://localhost:4200")
public class ResenaController {
	
	@Autowired
	private Resena resena;

    @Autowired
    private ResenaService resenaService;
    
    @Autowired
    ProductoService productoService;
        
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private UsuarioService usuarioService;
    
        
    @GetMapping("/lista")
    public ResponseEntity<List<ResenaDto>> list() {
        List<Resena> resena = resenaService.list();
        List<ResenaDto> resenaDtos = resena.stream().map(resena -> {
            ResenaDto resenaDto = new ResenaDto();
            BeanUtils.copyProperties(resena, resenaDto);
            return resenaDto;
        }).collect(Collectors.toList());
        
        return new ResponseEntity<>(resenaDtos, HttpStatus.OK);
    }

   
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") int id) {
        Optional<Resenas> resenaOptional = resenaService.getOne(id);
        if (!resenaOptional.isPresent())
            return new ResponseEntity<>(new Mensaje("No existe la reseña"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(resenaOptional.get(), HttpStatus.OK);
    }

}
