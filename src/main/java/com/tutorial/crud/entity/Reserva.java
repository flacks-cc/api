package com.tutorial.crud.entity;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.tutorial.crud.security.entity.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Reservaciones")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reserva")
	private Long idReserva;

	@Future(message = "La fecha no puede ser antes de la fecha actual")
	@NotNull(message = "La fecha no puede ser nula")
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;

	@NotNull(message = "La hora de inicio no puede ser nula")
	@Column(name = "hora_inicio", nullable = false)
	private LocalTime horaInicio;

	@NotNull(message = "La hora de fin no puede ser nula")
	@Column(name = "hora_fin", nullable = false)
	private LocalTime horaFin;

	@NotNull(message = "El estado de la reserva no puede ser nulo")
	@Column(name = "activa", nullable = false)
	private boolean activa;

	@ManyToOne
	@JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
	private Servicio servicio;

	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_usuario")
	private Usuario cliente;

	@ManyToOne
	@JoinColumn(name = "id_empleado", referencedColumnName = "id_usuario")
	private Usuario empleado;

	// Constructor vacío
	public Reserva() {
	}

	// Constructor con todos los atributos
	public Reserva(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, boolean activa, Servicio servicio,
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

	// Método toString
	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", fecha=" + fecha + ", horaInicio=" + horaInicio + ", horaFin="
				+ horaFin + ", activa=" + activa + ", servicio=" + servicio + ", cliente=" + cliente + ", empleado="
				+ empleado + "]";
	}
}
