package com.tutorial.crud.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.security.entity.Usuario;

public class ReservaDto {

	private Long idReserva;

	private LocalDate fecha;

	private LocalTime horaInicio;

	private LocalTime horaFin;

	private boolean activa;
	

	private Long idCliente;

	private Long idProducto;

	private Long idEmpleado;


	private Long idServicio;

	private Servicio servicio;

	private Usuario cliente;

	private Usuario empleado;

	// Constructor vacío
	public ReservaDto() {
	}

	// Constructor con todos los atributos
	public ReservaDto(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, boolean activa, Servicio servicio,
			Usuario cliente, Usuario empleado) {
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.activa = activa;
		this.servicio = servicio;
		this.cliente = cliente;
		this.empleado = empleado;
	}
	

	// Getters y setters
	
	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public Long getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Usuario getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
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
}
