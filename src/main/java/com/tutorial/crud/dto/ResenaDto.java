package com.tutorial.crud.dto;

import java.time.LocalDateTime;

public class ResenaDto {

	private Long idResena;

	private String mensaje;

	private int valoracion;

	private LocalDateTime fechaHora;

	private Long idCliente;

	private Long idEmpleado;

	private Long idProducto;

	private Long idServicio;

	
	// Constructor vacio
	public ResenaDto() {
	}

	// Constructor
	public ResenaDto(Long idResena, String mensaje, int valoracion, LocalDateTime fechaHora, Long idCliente,
			Long idProducto, Long idServicio) {
		this.idResena = idResena;
		this.mensaje = mensaje;
		this.valoracion = valoracion;
		this.fechaHora = fechaHora;
		this.idCliente = idCliente;
		this.idProducto = idProducto;
		this.idServicio = idServicio;
	}

	// Getters y Setters
	public Long getIdResena() {
		return idResena;
	}

	public void setIdResena(Long idResena) {
		this.idResena = idResena;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

}
