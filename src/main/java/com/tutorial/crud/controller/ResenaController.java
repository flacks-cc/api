package com.tutorial.crud.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@RequestMapping("/api/resenas")
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

	@PostMapping("/CreateUserReview")
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody ResenaDto resenaDto) {
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
	        Long idServicio = resenaDto.getIdServicio().longValue(); // Convertir Integer a Long
	        servicio = servicioService.findById(idServicio)
	                .orElseThrow(() -> new IllegalArgumentException("El servicio especificado no existe"));
	    }
	    // Obtener el producto a partir del ID proporcionado, si existe
	    Producto producto = null;
	    if (resenaDto.getIdProducto() != null) {
	        Long idProducto = resenaDto.getIdProducto().longValue(); // Convertir Integer a Long
	        producto = productoService.findById(idProducto)
	                .orElseThrow(() -> new IllegalArgumentException("El producto especificado no existe"));
	    }
	    // Crear la reseña con la fecha y hora actual, el usuario actual, el servicio y el producto
	    Resena resena = new Resena(
	            resenaDto.getMensaje(),
	            resenaDto.getValoracion(),
	            fechaHoraActual,
	            usuario,
	            producto,
	            servicio
	    );
	    // Guardar la reseña en la base de datos
	    try {
	        resenaService.save(resena);
	        return ResponseEntity.ok(new Mensaje("Reseña creada correctamente"));
	    } catch (Exception e) {
	        return new ResponseEntity<>(new Mensaje("Error al crear la reseña: " + e.getMessage()), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@GetMapping("/getAllReviews")
	public ResponseEntity<List<Map<String, Object>>> getAllReviews() {
	    // Obtener todas las reseñas de la base de datos
	    List<Resena> resenas = resenaService.findAll();
	    
	    // Convertir las reseñas a una lista de mapas con los campos necesarios
	    List<Map<String, Object>> resenasMap = new ArrayList<>();
	    for (Resena resena : resenas) {
	        Map<String, Object> resenaMap = new HashMap<>();
	        resenaMap.put("idResena", resena.getIdResena());
	        resenaMap.put("mensaje", resena.getMensaje());
	        resenaMap.put("valoracion", resena.getValoracion());
	        resenaMap.put("fechaHora", resena.getFechaHora());
	        resenaMap.put("cliente", resena.getCliente().getNombre()); // Obtener solo el nombre del cliente
	        resenaMap.put("producto", resena.getProducto().getNombre()); // Obtener solo el nombre del producto
	        resenaMap.put("servicio", resena.getServicio().getNombre()); // Obtener solo el nombre del servicio
	        resenasMap.add(resenaMap);
	    }
	    return new ResponseEntity<>(resenasMap, HttpStatus.OK);
	}

	
	//Obtener reseña por id
	@GetMapping("/getReviewById/{idResena}")
	public ResponseEntity<Object> getReviewById(@PathVariable("idResena") Long idResena) {
	    if (!resenaService.existsById(idResena))
	        return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
	    Resena resena = resenaService.findById(idResena).get();
	    return new ResponseEntity<>(resena, HttpStatus.OK);
	}

    @DeleteMapping("/deleteReview/{idResena}")
    public ResponseEntity<?> delete(@PathVariable("idResena") Long idResena) {
        // Verificar si la reseña existe
        if (!resenaService.existsById(idResena)) {
            return new ResponseEntity<>(new Mensaje("No existe la reseña"), HttpStatus.NOT_FOUND);
        }
        // Eliminar la reseña
        resenaService.deleteById(idResena);
        // Devolver un mensaje de éxito
        return new ResponseEntity<>(new Mensaje("Reseña eliminada correctamente"), HttpStatus.OK);
    }
}