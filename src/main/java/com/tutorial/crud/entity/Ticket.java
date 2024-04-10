package com.tutorial.crud.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.tutorial.crud.security.entity.Usuario;

@Entity
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime fechaImpresion;
    
    private double montoTotal; 

    private LocalDateTime fechaPago;

    private double montoPagado; 

    private double cambio; 
    
    @Size(max = 50)
    private String nombreEmpleado;

    @ManyToOne
    @JoinColumn(name = "idDetalle", nullable = false)
    private DetalleGeneral detalleGeneral;

    @ManyToOne
    @JoinColumn(name = "idMetodo", nullable = true)
    private MetodoPago metodoPago;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @Valid 
    private Usuario usuario;

    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Ticket() {
    }

	// Constructor con todos los campos
    public Ticket(LocalDateTime fechaImpresion, double montoTotal, LocalDateTime fechaPago, double montoPagado, double cambio, String nombreEmpleado, MetodoPago metodoPago, DetalleGeneral detalleGeneral, Usuario usuario) {
        this.fechaImpresion = fechaImpresion;
        this.montoTotal = montoTotal;
        this.fechaPago = fechaPago;
        this.montoPagado = montoPagado;
        this.cambio = cambio;
        this.nombreEmpleado = nombreEmpleado;
        this.metodoPago = metodoPago;
        this.detalleGeneral = detalleGeneral;
        this.usuario = usuario;
    }
    
    public Ticket(LocalDateTime fechaImpresion, double montoTotal, DetalleGeneral detalleGeneral, Usuario usuario) {
        this.fechaImpresion = fechaImpresion;
        this.montoTotal = montoTotal;
        this.detalleGeneral = detalleGeneral;
        this.usuario = usuario;
    }


    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(LocalDateTime fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
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

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public DetalleGeneral getDetalleGeneral() {
		return detalleGeneral;
	}

	public void setDetalleGeneral(DetalleGeneral detalleGeneral) {
		this.detalleGeneral = detalleGeneral;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}
}
