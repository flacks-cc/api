package com.tutorial.crud.dto;

import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Reserva;

public class DetalleProductoDto {

	private int cantidad;

	private double total;

	private Reserva reserva;

	private Producto producto;

	public Long getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	private Long idReserva;

	private Long idProducto;

	// Constructor vacío
	public DetalleProductoDto() {
	}

	// Constructor con todos los atributos
	public DetalleProductoDto(int cantidad, double total, Reserva reserva, Producto producto) {
		this.cantidad = cantidad;
		this.total = total;
		this.reserva = reserva;
		this.producto = producto;
	}

	// Constructor con todas las propiedades
    public DetalleProductoDto(int cantidad, double total, Producto producto, Reserva reserva) {
        this.cantidad = cantidad;
        this.total = total;
        this.producto = producto;
        this.reserva = reserva;

        if (reserva != null) {
            this.idReserva = reserva.getIdReserva();
        }
        if (producto != null) {
            this.idProducto = producto.getIdProducto();
        }
    }
	// Getters y setters
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

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public static class ProductoDetailsDTO {
		private String nombre;
		private double precio;
		private int cantidad;

		// Constructor, getters y setters

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public double getPrecio() {
			return precio;
		}

		public void setPrecio(double precio) {
			this.precio = precio;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
	}
}
