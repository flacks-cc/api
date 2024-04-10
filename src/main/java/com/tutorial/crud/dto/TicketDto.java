package com.tutorial.crud.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.tutorial.crud.dto.DetalleProductoDto.ProductoDetailsDTO;
import com.tutorial.crud.entity.MetodoPago;
import com.tutorial.crud.entity.Reserva;
import com.tutorial.crud.security.entity.Usuario;

public class TicketDto {

	private Long idTicket;

	private LocalDateTime fechaHoraExpedicion;

	private double montoTotal;

	private LocalDateTime fechaHoraPago;

	private double montoPagado;

	private double cambio;

	private MetodoPago metodoPago;

	private Usuario empleado;

	private Reserva reserva;

	// Constructor vacío
	public TicketDto() {
	}

	// Constructor con todos los atributos
	public TicketDto(LocalDateTime fechaHoraExpedicion, double montoTotal, LocalDateTime fechaHoraPago,
			double montoPagado, double cambio, MetodoPago metodoPago, Usuario empleado, Reserva reserva) {
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

	public static class TicketDetailsDTO {
		private LocalDateTime fechaHoraExpedicion;
		private double montoTotal;
		private LocalDateTime fechaHoraPago;
		private double montoPagado;
		private double cambio;
		private String metodoPago;
		private String nombreCompletoEmpleado;
		private LocalDate fechaReserva;
		private LocalTime horaReserva;
		private String nombreServicio;
		private double precioServicio;
		public List<ProductoDetailsDTO> productos;

		// Constructor, getters y setters
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

		public String getMetodoPago() {
			return metodoPago;
		}

		public void setMetodoPago(String metodoPago) {
			this.metodoPago = metodoPago;
		}

		public String getNombreCompletoEmpleado() {
			return nombreCompletoEmpleado;
		}

		public void setNombreCompletoEmpleado(String nombreCompletoEmpleado) {
			this.nombreCompletoEmpleado = nombreCompletoEmpleado;
		}

		public LocalDate getFechaReserva() {
			return fechaReserva;
		}

		public void setFechaReserva(LocalDate fechaReserva) {
			this.fechaReserva = fechaReserva;
		}

		public LocalTime getHoraReserva() {
			return horaReserva;
		}

		public void setHoraReserva(LocalTime horaReserva) {
			this.horaReserva = horaReserva;
		}

		public String getNombreServicio() {
			return nombreServicio;
		}

		public void setNombreServicio(String nombreServicio) {
			this.nombreServicio = nombreServicio;
		}

		public double getPrecioServicio() {
			return precioServicio;
		}

		public void setPrecioServicio(double precioServicio) {
			this.precioServicio = precioServicio;
		}

		public List<ProductoDetailsDTO> getProductos() {
			return productos;
		}

		public void setProductos(List<ProductoDetailsDTO> productos) {
			this.productos = productos;
		}
	}
}
