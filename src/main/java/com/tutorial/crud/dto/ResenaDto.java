package com.tutorial.crud.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.security.entity.Usuario;

public class ResenaDto {

    private Integer id;

    private String mensaje;
    
    private Integer valoracion;


    private LocalDateTime fechaResena;

    @NotNull(message = "El id de usuario debe ser ingresado y no puede ser nulo")
    private Integer idUsuario;

    private Integer idServicio;
    
    private Integer idProducto;

	private Servicio servicio;

	private Usuario usuario;

	private Producto producto;

    public ResenaDto() {
    }
    
 public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public LocalDateTime getFechaResena() {
		return fechaResena;
	}

	public void setFechaResena(LocalDateTime fechaResena) {
		this.fechaResena = fechaResena;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// Constructor con Usuario, Servicio y Producto
    public ResenaDto(Integer id, String mensaje, Integer valoracion, LocalDateTime fechaResena, Servicio servicio, Usuario usuario, Producto producto) {
    	this.id = id;
    	this.mensaje = mensaje;
        this.valoracion = valoracion;
        this.fechaResena = fechaResena;
        
        if (usuario != null) {
            this.idUsuario = usuario.getId();
            this.usuario = usuario;
        }
        
        if (servicio != null) {
            this.idServicio = servicio.getId();
            this.servicio = servicio;
        }
        
        if (producto != null) {
            this.idProducto = producto.getId();
            this.producto = producto;
        }
    }
}
