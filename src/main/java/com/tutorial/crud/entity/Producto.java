package com.tutorial.crud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProducto")
	private Long idProducto;

	@NotNull(message = "El nombre no puede ser nulo")
	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private String nombre;

	@Column(name = "descripcion", length = 255)
	private String descripcion;

	@NotNull(message = "El precio no puede ser nulo")
	@Min(value = 0, message = "El precio debe ser mayor o igual a 0")
	@Column(name = "precio", nullable = false)
	private double precio;

	@NotNull(message = "El stock no puede ser nulo")
	@Min(value = 0, message = "El stock debe ser mayor o igual a 0")
	@Column(name = "stock", nullable = false)
	private int stock;

	// Constructor vacío
	public Producto() {
	}

	// Constructor con todos los atributos
	public Producto(String nombre, String descripcion, double precio, int stock) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
	}

	// Getters y setters
	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	// Método toString
	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", precio=" + precio + ", stock=" + stock + "]";
	}
}
