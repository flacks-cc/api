package com.tutorial.crud.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import com.tutorial.crud.security.entity.Usuario;

@Entity
public class Resenas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fecha_resena")
    private LocalDateTime  fechaResena;

    private Integer valoracion;

    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @Valid // Agregamos la anotación @Valid
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = true)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idServicio", nullable = true)
    private Servicio servicio;

    // Getters y setters

 // Constructor sin argumentos
    public Resenas() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime  getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(LocalDateTime  fechaResena) {
        this.fechaResena = fechaResena;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    
    public Resenas(String mensaje, int valoracion, LocalDateTime fechaResena, Servicio servicio, Usuario usuario, Producto producto) {
        this.mensaje = mensaje;
        this.valoracion = valoracion;
        this.fechaResena = fechaResena;
        this.servicio = servicio;
        this.usuario = usuario;
        this.producto = producto;
}
}