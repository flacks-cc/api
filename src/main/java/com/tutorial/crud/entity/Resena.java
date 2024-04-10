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
import javax.validation.constraints.PositiveOrZero;
import com.tutorial.crud.security.entity.Usuario;

@Entity
@Table(name = "Resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena")
    private Long idResena;

    @Column(name = "mensaje", length = 255)
    private String mensaje;

    @NotNull(message = "La valoración no puede ser nula")
    @PositiveOrZero(message = "La valoración debe ser mayor o igual a 0")
    @Column(name = "valoracion", nullable = false)
    private int valoracion;

    @NotNull(message = "La fecha y hora no pueden ser nulas")
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @NotNull(message = "El cliente no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "fk_resena_cliente"))
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "id_producto", foreignKey = @ForeignKey(name = "fk_resena_producto"))
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_servicio", foreignKey = @ForeignKey(name = "fk_resena_servicio"))
    private Servicio servicio;

    // Constructor vacío
    public Resena() {
    }

    // Constructor lleno
    public Resena(String mensaje, int valoracion, LocalDateTime fechaHora, Usuario cliente, Producto producto, Servicio servicio) {
        this.mensaje = mensaje;
        this.valoracion = valoracion;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.producto = producto;
        this.servicio = servicio;
    }

    // Getters y setters
    public Long getIdResena() {
        return idResena;
    }

    public void setIdResena(Long idResena) {
        this.idResena = idResena;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
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

    // Método toString
    @Override
    public String toString() {
        return "Resena [idResena=" + idResena + 
        		", mensaje=" + mensaje + 
        		", valoracion=" + valoracion + 
        		", fechaHora=" + fechaHora + 
        		", cliente=" + cliente + 
                ", producto=" + producto + 
                ", servicio=" + servicio + "]";
    }
}
