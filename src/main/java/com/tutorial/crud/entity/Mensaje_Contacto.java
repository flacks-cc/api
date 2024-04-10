package com.tutorial.crud.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tutorial.crud.security.entity.Usuario;

@Entity
@Table(name = "Mensajes_Contacto")
public class Mensaje_Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Long idMensaje;

    @NotNull(message = "El asunto no puede ser nulo")
    @Size(max = 255, message = "Se acepta un máximo de 255 caracteres")
    @Column(name = "asunto", nullable = false, length = 255)
    private String asunto;

    @NotNull(message = "El mensaje no puede ser nulo")
    @Size(max = 255, message = "Se acepta un máximo de 255 caracteres")
    @Column(name = "mensaje", nullable = false, length = 255)
    private String mensaje;

    @Column(name = "adjunto", length = 255)
    private String adjunto;

    @NotNull(message = "La fecha y hora no pueden ser nulas")
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @NotNull(message = "El cliente no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "fk_mensaje_cliente"))
    private Usuario cliente;

    // Constructor vacío
    public Mensaje_Contacto() {
    }

    // Constructor lleno
    public Mensaje_Contacto(String asunto, String mensaje, String adjunto, LocalDateTime fechaHora, Usuario cliente) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.adjunto = adjunto;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
    }

    // Getters y setters
    public Long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Long idMensaje) {
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

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    // Método toString
    @Override
    public String toString() {
        return "MensajeContacto [idMensaje=" + idMensaje + 
        		", asunto=" + asunto + 
        		", mensaje=" + mensaje + 
        		", adjunto=" + adjunto + 
        		", fechaHora=" + fechaHora + 
        		", cliente=" + cliente + "]";
    }
}
