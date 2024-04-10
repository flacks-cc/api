package com.tutorial.crud.dto;

public class Metodos_PagoDto {
    private Integer idMetodoPago;
    private String nombre;

    // Constructor
    public Metodos_PagoDto(Integer idMetodoPago, String nombre) {
        this.idMetodoPago = idMetodoPago;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Integer getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
