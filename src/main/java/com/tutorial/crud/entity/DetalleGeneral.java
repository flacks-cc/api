package com.tutorial.crud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import com.tutorial.crud.security.entity.Usuario;

@Entity
public class DetalleGeneral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idReservacion", nullable = true)
    private Reservacion reservacion;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = true)
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @Valid 
    private Usuario usuario;

    //Campo para la cantidad de productos adquiridos
    private int cantidad;
    
    //Campo para almacenar el total de la cantidad de productos adquiridos * el precio del producto
    private double total; 

    public DetalleGeneral() {
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DetalleGeneral(Reservacion reservacion, Producto producto, Usuario usuario, int cantidad, double total) {
        this.reservacion = reservacion;
        this.usuario = usuario; 
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
    }

}