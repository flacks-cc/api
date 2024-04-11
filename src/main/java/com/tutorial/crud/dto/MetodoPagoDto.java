package com.tutorial.crud.dto;

public class MetodoPagoDto {

	private Long idMetodoPago;

	private String nombre;

	// Constructor vacio
	public MetodoPagoDto() {
	}

	// Constructor
	public MetodoPagoDto(Long idMetodoPago, String nombre) {
		this.idMetodoPago = idMetodoPago;
		this.nombre = nombre;
	}

	// Getters y Setters
	public Long getIdMetodoPago() {
		return idMetodoPago;
	}

	public void setIdMetodoPago(Long idMetodoPago) {
		this.idMetodoPago = idMetodoPago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}