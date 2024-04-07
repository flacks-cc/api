package com.tutorial.crud.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.security.entity.Usuario;

public class ReservacionDto {

    @NotNull(message = "La fecha de reserva debe ser ingresada y no puede ser nula")
    private LocalDate fechaReserva;

    @NotNull(message = "La hora de inicio debe ser ingresada y no puede ser nula")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin debe ser ingresada y no puede ser nula")
    private LocalTime horaFin;

    @NotNull(message = "El id de usuario debe ser ingresado y no puede ser nulo")
    private int idUsuario;

    @NotNull(message = "El id del servicio debe ser ingresado y no puede ser nulo")
    private int idServicio;

	private Servicio servicio;

	private Usuario usuario;

    public ReservacionDto() {
    }
    
    public ReservacionDto(LocalTime horaInicio, LocalTime horaFin, Servicio servicio, Usuario usuario) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.servicio = servicio;
        this.usuario = usuario;
    }

    // Constructor con Usuario y Servicio
    public ReservacionDto(LocalDate fechaReserva, LocalTime horaInicio, LocalTime horaFin, Usuario usuario, Servicio servicio) {
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        if (usuario != null) {
            this.idUsuario = usuario.getId();
        }
        if (servicio != null) {
            this.idServicio = servicio.getId();
        }
    }
    // Getters and setters
    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
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
}
