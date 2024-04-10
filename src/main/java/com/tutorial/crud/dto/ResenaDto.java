package com.tutorial.crud.dto;

import java.time.LocalDateTime;

public class ResenaDto {

	private Integer idResena;

	private String mensaje;

	private Integer valoracion;

	private LocalDateTime fechaHora;

	private Integer idCliente;

	private Integer idProducto;

	private Integer idServicio;

	// Constructor vacio
	public ResenaDto() {
	}

	// Constructor
	public ResenaDto(Integer idResena, String mensaje, Integer valoracion, LocalDateTime fechaHora, Integer idCliente,
			Integer idProducto, Integer idServicio) {
		this.idResena = idResena;
		this.mensaje = mensaje;
		this.valoracion = valoracion;
		this.fechaHora = fechaHora;
		this.idCliente = idCliente;
		this.idProducto = idProducto;
		this.idServicio = idServicio;
	}

	// Getters y Setters
	public Integer getIdResena() {
		return idResena;
	}

	public void setIdResena(Integer idResena) {
		this.idResena = idResena;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}
}
