package com.tutorial.crud.controller;

import java.time.LocalDateTime;
import java.util.List;
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
import com.tutorial.crud.entity.Resenas;
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
    private ResenaService resenaService;
    
    @Autowired
    ProductoService productoService;
        
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<ResenaDto>> list() {
        List<Resenas> resenas = resenaService.list();
        List<ResenaDto> resenaDtos = resenas.stream().map(resena -> {
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crearadmin")
    public ResponseEntity<?> crearAdmin(@RequestBody @Valid ResenaDto resenaDto) {
        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        
        // Obtener el usuario actual
        Usuario usuario = usuarioService.findById(resenaDto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("El usuario especificado no existe"));
        
        // Obtener el servicio a partir del ID proporcionado
        Servicio servicio = servicioService.findById(resenaDto.getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("El servicio especificado no existe"));
        
        // Obtener el producto a partir del ID proporcionado
        Producto producto = productoService.findById(resenaDto.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe"));
        
        // Crear la reseña con la fecha y hora actual, el usuario actual, el servicio y el producto
        Resenas resena = new Resenas(
                resenaDto.getMensaje(),
                resenaDto.getValoracion(),
                fechaHoraActual,
                servicio,
                usuario,
                producto
        );
        
        // Guardar la reseña en la base de datos
        resenaService.save(resena);
        
        // Devolver una respuesta exitosa
        return ResponseEntity.ok(new Mensaje("Reseña creada correctamente"));
    }

    
    @PostMapping("/crearusuario")
    public ResponseEntity<?> crearUsuario(@RequestBody ResenaDto resenaDto) {
        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        
        // Obtener el usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioService.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
        
        // Obtener el servicio a partir del ID proporcionado, si existe
        Servicio servicio = null;
        if (resenaDto.getIdServicio() != null) {
            servicio = servicioService.findById(resenaDto.getIdServicio())
                    .orElseThrow(() -> new IllegalArgumentException("El servicio especificado no existe"));
        }
        
        // Obtener el producto a partir del ID proporcionado, si existe
        Producto producto = null;
        if (resenaDto.getIdProducto() != null) {
            producto = productoService.findById(resenaDto.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe"));
        }
        
        // Crear la reseña con la fecha y hora actual, el usuario actual, el servicio y el producto
        Resenas resena = new Resenas(
                resenaDto.getMensaje(),
                resenaDto.getValoracion(),
                fechaHoraActual,
                servicio,
                usuario,
                producto
        );
        
        // Guardar la reseña en la base de datos
        resenaService.save(resena);
        
        // Devolver una respuesta exitosa
        return ResponseEntity.ok(new Mensaje("Reseña creada correctamente"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!resenaService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe la reseña"), HttpStatus.NOT_FOUND);
        resenaService.delete(id);
        return new ResponseEntity<>(new Mensaje("Reseña eliminada correctamente"), HttpStatus.OK);
    }
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Valid ResenaDto resenaDto) {
        // Verificar si la reseña existe en la base de datos
        Optional<Resenas> resenaOptional = resenaService.getOne(id);
        if (!resenaOptional.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No existe la reseña con el ID proporcionado"), HttpStatus.NOT_FOUND);
        }

        // Obtener la reseña a actualizar
        Resenas resena = resenaOptional.get();

        // Actualizar los datos de la reseña con los nuevos valores proporcionados en resenaDto
        resena.setMensaje(resenaDto.getMensaje());
        resena.setValoracion(resenaDto.getValoracion());

        // Obtener el usuario actual y establecerlo en la reseña
        Usuario usuario = usuarioService.findById(resenaDto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("El usuario especificado no existe"));
        resena.setUsuario(usuario);

        // Guardar la reseña actualizada en la base de datos
        resenaService.save(resena);

        // Devolver una respuesta exitosa
        return ResponseEntity.ok(new Mensaje("Reseña actualizada correctamente"));
    }

}
