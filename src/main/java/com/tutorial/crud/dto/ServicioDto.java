package com.tutorial.crud.dto;

import java.time.Duration;

public class ServicioDto {

	private Long idServicio;

	private String nombre;

	private String descripcion;

	private double precio;

	private Duration duracion;

	// Constructor vacío
	public ServicioDto() {
	}

	// Constructor con todos los atributos
	public ServicioDto(String nombre, String descripcion, double precio, Duration duracion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.duracion = duracion;
	}

	// Getters y setters
	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
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

	public Duration getDuracion() {
		return duracion;
	}

	public void setDuracion(Duration duracion) {
		this.duracion = duracion;
	}
}