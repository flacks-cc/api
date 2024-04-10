package com.tutorial.crud.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.tutorial.crud.security.entity.Usuario;

@Entity
@Table(name = "Tickets")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ticket")
	private Long idTicket;

	@NotNull(message = "La fecha y hora de expedición no puede ser nula")
	@Column(name = "fecha_hora_expedicion", nullable = false)
	private LocalDateTime fechaHoraExpedicion;

	@NotNull(message = "El monto total no puede ser nulo")
	@Min(value = 1, message = "El monto total debe ser mayor a cero")
	@Column(name = "monto_total", nullable = false)
	private double montoTotal;

	@Column(name = "fecha_hora_pago")
	private LocalDateTime fechaHoraPago;

	@Min(value = 1, message = "El monto pagado debe ser mayor a cero")
	@Column(name = "monto_pagado")
	private double montoPagado;

	@Min(value = 0, message = "El cambio debe ser mayor o igual a cero")
	@Column(name = "cambio")
	private double cambio;

	@ManyToOne
	@JoinColumn(name = "id_metodo_pago")
	private MetodoPago metodoPago;

	@ManyToOne
	@NotNull(message = "El id del empleado no puede ser nulo")
	@JoinColumn(name = "id_empleado", nullable = false)
	private Usuario empleado;

	@ManyToOne
	@NotNull(message = "El id de la reserva no puede ser nulo")
	@JoinColumn(name = "id_reserva", nullable = false)
	private Reserva reserva;

	// Constructor vacío
	public Ticket() {
	}

	// Constructor con todos los atributos
	public Ticket(LocalDateTime fechaHoraExpedicion, double montoTotal, LocalDateTime fechaHoraPago, double montoPagado,
			double cambio, MetodoPago metodoPago, Usuario empleado, Reserva reserva) {
		this.fechaHoraExpedicion = fechaHoraExpedicion;
		this.montoTotal = montoTotal;
		this.fechaHoraPago = fechaHoraPago;
		this.montoPagado = montoPagado;
		this.cambio = cambio;
		this.metodoPago = metodoPago;
		this.empleado = empleado;
		this.reserva = reserva;
	}

	// Getters y setters
	public Long getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Long idTicket) {
		this.idTicket = idTicket;
	}

	public LocalDateTime getFechaHoraExpedicion() {
		return fechaHoraExpedicion;
	}

	public void setFechaHoraExpedicion(LocalDateTime fechaHoraExpedicion) {
		this.fechaHoraExpedicion = fechaHoraExpedicion;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public LocalDateTime getFechaHoraPago() {
		return fechaHoraPago;
	}

	public void setFechaHoraPago(LocalDateTime fechaHoraPago) {
		this.fechaHoraPago = fechaHoraPago;
	}

	public double getMontoPagado() {
		return montoPagado;
	}

	public void setMontoPagado(double montoPagado) {
		this.montoPagado = montoPagado;
	}

	public double getCambio() {
		return cambio;
	}

	public void setCambio(double cambio) {
		this.cambio = cambio;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public Usuario getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", fechaHoraExpedicion=" + fechaHoraExpedicion + ", montoTotal="
				+ montoTotal + ", fechaHoraPago=" + fechaHoraPago + ", montoPagado=" + montoPagado + ", cambio="
				+ cambio + ", metodoPago=" + metodoPago + ", empleado=" + empleado + ", reserva=" + reserva + "]";
	}
}
