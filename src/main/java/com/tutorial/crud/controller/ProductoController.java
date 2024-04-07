package com.tutorial.crud.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.tutorial.crud.dto.ProductoDto;
import com.tutorial.crud.entity.Categoria;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.service.CategoriaService;
import com.tutorial.crud.service.ProductoService;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @Autowired
    CategoriaService categoriaService;

    
    // Endpoint para obtener la lista de todos los productos
    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list() {
        List<Producto> list = productoService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Endpoint para obtener los detalles de un producto por su ID
    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") int id) {
        if (!productoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getOne(id).get();
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    // Endpoint para obtener los detalles de un producto por su nombre
    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Object> getByNombre(@PathVariable("nombre") String nombre) {
        if (!productoService.existsByNombre(nombre))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getByNombre(nombre).get();
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    // Endpoint para eliminar un producto
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        if (!productoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity<>(new Mensaje("Producto eliminado"), HttpStatus.OK);
    }
    
 // Endpoint para crear un nuevo producto
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ProductoDto productoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
        }

        // Verificar si ya existe un producto con el mismo nombre
        if (productoService.existsByNombre(productoDto.getNombre())) {
            return ResponseEntity.badRequest().body(new Mensaje("Ya existe un producto con el mismo nombre"));
        }

        Optional<Categoria> categoriaOptional = categoriaService.findById(productoDto.getIdCategoria());
        if (!categoriaOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new Mensaje("La categoría especificada no existe"));
        }

        Producto producto = new Producto(
            productoDto.getNombre(),
            productoDto.getDescripcion(),
            productoDto.getCantidadTotal(),
            productoDto.getPrecio(),
            categoriaOptional.get() 
        );
        productoService.save(producto);
        return ResponseEntity.ok(new Mensaje("Producto creado exitosamente"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Valid ProductoDto productoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(new Mensaje(errorMessage.toString()));
        }

        // Verificar si el producto con el ID especificado existe
        Optional<Producto> existingProductoOptional = productoService.getOne(id);
        if (!existingProductoOptional.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        }

        Producto existingProducto = existingProductoOptional.get();

        // Verificar si se está intentando actualizar el nombre del producto a uno que ya existe
        if (!existingProducto.getNombre().equals(productoDto.getNombre()) && productoService.existsByNombre(productoDto.getNombre())) {
            return ResponseEntity.badRequest().body(new Mensaje("Ya existe un producto con el mismo nombre"));
        }

        Optional<Categoria> categoriaOptional = categoriaService.findById(productoDto.getIdCategoria());
        if (!categoriaOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new Mensaje("La categoría especificada no existe"));
        }

        existingProducto.setNombre(productoDto.getNombre());
        existingProducto.setDescripcion(productoDto.getDescripcion());
        existingProducto.setCantidadTotal(productoDto.getCantidadTotal());
        existingProducto.setPrecio(productoDto.getPrecio());
        existingProducto.setCategoria(categoriaOptional.get());
        productoService.save(existingProducto);

        return ResponseEntity.ok(new Mensaje("Producto actualizado exitosamente"));
    }


}
