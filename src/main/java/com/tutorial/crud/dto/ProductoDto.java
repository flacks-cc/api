package com.tutorial.crud.dto;

public class ProductoDto {

    private Long id_producto;
    
    private String nombre;
    
    private String descripcion;
    
    private double precio;
    
    private int stock;

    // Constructor vacío
    public ProductoDto() {
    }

    // Constructor con todos los atributos
    public ProductoDto(Long id_producto, String nombre, String descripcion, double precio, int stock) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y setters
    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}