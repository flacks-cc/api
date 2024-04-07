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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tutorial.crud.security.entity.Usuario;

@Entity
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @NotNull
    @Size(max = 50)
    private String asunto; 

    @NotNull
    private String mensaje;
    
    private String adjunto; 

    @Column(name = "fecha_mensaje")
    private LocalDateTime fechaMensaje;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @Valid 
    private Usuario usuario;

    public Contacto() {
        // Constructor por defecto necesario para JPA
    }

    public Contacto(String asunto, String mensaje, String adjunto, LocalDateTime fechaMensaje, Usuario usuario) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.adjunto = adjunto;
        this.fechaMensaje = fechaMensaje;
        this.usuario = usuario;
    }
    
    

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    public LocalDateTime getFechaMensaje() {
        return fechaMensaje;
    }

    public void setFechaMensaje(LocalDateTime fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
