package com.tutorial.crud.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.tutorial.crud.security.entity.Usuario;

public class ContactoDto {

    private String asunto;
    private String mensaje;
    private String adjunto;

    private LocalDateTime fechaMensaje;

    @NotNull(message = "El id de usuario debe ser ingresado y no puede ser nulo")
    private int idUsuario;

    private Usuario usuario;

    public ContactoDto() {
    }

    // Constructor con Usuario
    public ContactoDto(String asunto, String mensaje, String adjunto, LocalDateTime fechaMensaje, Usuario usuario) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.adjunto = adjunto;
        this.fechaMensaje = fechaMensaje;

        if (usuario != null) {
            this.idUsuario = usuario.getId();
            this.usuario = usuario;
        }
    }
    
    // Getters y Setters

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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
