package com.tutorial.crud.dto;


import javax.validation.constraints.NotBlank;

public class CategoriaDto {

    private int id;

    @NotBlank(message = "El nombre de la categoria es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción de la categoria es obligatorio")
    private String descripcion;

    public CategoriaDto() {
    }

    public CategoriaDto(int id, @NotBlank String nombre, @NotBlank String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
