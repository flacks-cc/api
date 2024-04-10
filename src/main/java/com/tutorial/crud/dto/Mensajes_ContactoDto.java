package com.tutorial.crud.dto;

import java.time.LocalDateTime;

public class Mensajes_ContactoDto {
    private Integer idMensaje;
    private String asunto;
    private String mensaje;
    private String adjunto;
    private LocalDateTime fechaHora;
    private Integer idCliente;

    // Constructor
    public Mensajes_ContactoDto(Integer idMensaje, String asunto, String mensaje, String adjunto, LocalDateTime fechaHora, Integer idCliente) {
        this.idMensaje = idMensaje;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.adjunto = adjunto;
        this.fechaHora = fechaHora;
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public Integer getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
}