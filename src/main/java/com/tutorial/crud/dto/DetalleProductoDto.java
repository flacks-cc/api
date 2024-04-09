package com.tutorial.crud.dto;

import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Reservacion;
import com.tutorial.crud.security.entity.Usuario;
import javax.validation.constraints.NotNull;

public class DetalleProductoDto {

	@NotNull(message = "El id de usuario debe ser ingresado y no pued+99999+e ser nulo")
	private int idUsuario;

	@NotNull(message = "El id de reservación debe ser ingresado y no puede ser nulo")
	private Integer idReservacion;

	@NotNull(message = "El id de producto debe ser ingresado y no puede ser nulo")
	private Integer idProducto;

	private int cantidad;

	@NotNull(message = "El total debe ser ingresado y no puede ser nulo")
	private double total;

	private Reservacion reservacion;

	private Producto producto;

	private Usuario usuario;

	// Constructor sin argumentos requerido por Jackson
	public DetalleProductoDto() {
		// deja este constructor vacío o inicializa tus campos si es necesario
	}

	// Constructor con Usuario y Servicio
	public DetalleProductoDto(int cantidad, double total, Producto producto, Usuario usuario, Reservacion reservacion) {
		this.cantidad = cantidad;
		this.total = total;
		this.producto = producto;
		this.usuario = usuario;
		this.reservacion = reservacion;

		if (usuario != null) {
			this.idUsuario = usuario.getId();
		}
		if (reservacion != null) {
			this.idReservacion = reservacion.getId();
		}
		if (producto != null) {
			this.idProducto = producto.getId();
		}
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdReservacion() {
		return idReservacion;
	}

	public void setIdReservacion(Integer idReservacion) {
		this.idReservacion = idReservacion;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Reservacion getReservacion() {
		return reservacion;
	}

	public void setReservacion(Reservacion reservacion) {
		this.reservacion = reservacion;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DetalleProductoDto(int cantidad, double total, Reservacion reservacion, Producto producto, Usuario usuario) {
		this.cantidad = cantidad;
		this.total = total;
		this.reservacion = reservacion;
		this.producto = producto;

		// Establecer idProducto si el producto no es nulo
		if (producto != null) {
			this.idProducto = producto.getId();
		}

		if (usuario != null) {
			this.idUsuario = usuario.getId();
		}

		// Establecer idUsuario e idReservacion si la reservación no es nula
		if (reservacion != null) {
			this.idReservacion = reservacion.getId();
		}
	}
}
