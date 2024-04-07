package com.tutorial.crud.dto;

import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.tutorial.crud.entity.Categoria;
import com.tutorial.crud.service.CategoriaService;

public class ProductoDto {

    @NotNull(message = "El nombre debe ser ingresado y no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "La descripción debe ser ingresada y no puede ser nula")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "La cantidad debe ser ingresada y no puede ser nula")
    @Min(value = 0, message = "La cantidad total debe ser mayor o igual a cero")
    private Integer cantidadTotal;

    @NotNull(message = "El precio debe ser ingresado y no puede ser nulo")
    @Min(value = 0, message = "El precio debe ser mayor o igual a cero")    
    private Float precio;

    @NotNull(message = "El ID de la categoría debe ser ingresado y no puede ser nulo")
    private Integer  idCategoria;

    public ProductoDto() {
    }

    public ProductoDto(String nombre, String descripcion, Integer cantidadTotal, Float precio, Integer  idCategoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadTotal = cantidadTotal;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer  getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer  idCategoria) {
        this.idCategoria = idCategoria;
    }
    
}
