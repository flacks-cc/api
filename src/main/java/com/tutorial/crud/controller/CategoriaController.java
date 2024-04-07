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

import com.tutorial.crud.dto.CategoriaDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.Categoria;
import com.tutorial.crud.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<CategoriaDto>> list(){
        List<Categoria> categorias = categoriaService.list();
        List<CategoriaDto> categoriasDto = categorias.stream().map(categoria -> {
            CategoriaDto categoriaDto = new CategoriaDto();
            BeanUtils.copyProperties(categoria, categoriaDto);
            return categoriaDto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(categoriasDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if(!categoriaService.existsById(id))
            return new ResponseEntity<>(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Categoria categoria = categoriaService.getOne(id).get();
        CategoriaDto categoriaDto = new CategoriaDto();
        BeanUtils.copyProperties(categoria, categoriaDto);
        return new ResponseEntity<>(categoriaDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CategoriaDto categoriaDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
        }
        if(categoriaService.existsByNombre(categoriaDto.getNombre()))
            return new ResponseEntity<>(new Mensaje("El nombre de esa categoria ya existe"), HttpStatus.BAD_REQUEST);

        if(categoriaDto.getDescripcion() == null || categoriaDto.getDescripcion().isEmpty())
            return new ResponseEntity<>(new Mensaje("La descripción de la categoria debe ser añadida"), HttpStatus.BAD_REQUEST);

        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDto.getNombre());
        categoria.setDescripcion(categoriaDto.getDescripcion());
        categoriaService.save(categoria);
        return new ResponseEntity<>(new Mensaje("Categoría creada exitosamenet"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody @Valid CategoriaDto categoriaDto, BindingResult bindingResult){
        if(!categoriaService.existsById(id))
            return new ResponseEntity<>(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return new ResponseEntity<>(new Mensaje(errorMessage.toString()), HttpStatus.BAD_REQUEST);
        }
        if(categoriaService.existsByNombre(categoriaDto.getNombre()) && categoriaService.getByNombre(categoriaDto.getNombre()).get().getId() != id)
            return new ResponseEntity<>(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(categoriaDto.getNombre()==null || categoriaDto.getNombre().isEmpty())
            return new ResponseEntity<>(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(categoriaDto.getDescripcion() == null || categoriaDto.getDescripcion().isEmpty())
            return new ResponseEntity<>(new Mensaje("la descripcion es obligatoria"), HttpStatus.BAD_REQUEST);

        Categoria categoria = new Categoria();
        BeanUtils.copyProperties(categoriaDto, categoria);
        categoria.setId(id);
        categoriaService.save(categoria);
        return new ResponseEntity<>(new Mensaje("categoria actualizada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!categoriaService.existsById(id))
            return new ResponseEntity<>(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        categoriaService.delete(id);
        return new ResponseEntity<>(new Mensaje("categoria eliminada"), HttpStatus.OK);
    }
}
