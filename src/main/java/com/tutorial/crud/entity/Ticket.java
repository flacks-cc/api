package com.tutorial.crud.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.tutorial.crud.security.entity.Usuario;

@Entity
@Table(name = "Tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long idTicket;

    @Column(name = "fecha_hora_expedicion", nullable = true)
    private LocalDateTime fechaHoraExpedicion;

    @Column(name = "monto_total", nullable = true)
    private double montoTotal;

    @Column(name = "fecha_hora_pago")
    private LocalDateTime fechaHoraPago;

    @Column(name = "monto_pagado")
    private double montoPagado;

    @Column(name = "cambio")
    private double cambio;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = true)
    private Usuario empleado;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = true)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "id_detalle_producto", nullable = true)
    private DetalleProducto detalleProducto;

    // Constructor vacío
    public Ticket() {
    }

    // Constructor con todos los atributos
    public Ticket(LocalDateTime fechaHoraExpedicion, double montoTotal, LocalDateTime fechaHoraPago, double montoPagado,
                  double cambio, MetodoPago metodoPago, Usuario empleado, Reserva reserva, DetalleProducto detalleProducto) {
        this.fechaHoraExpedicion = fechaHoraExpedicion;
        this.montoTotal = montoTotal;
        this.fechaHoraPago = fechaHoraPago;
        this.montoPagado = montoPagado;
        this.cambio = cambio;
        this.metodoPago = metodoPago;
        this.empleado = empleado;
        this.reserva = reserva;
        this.detalleProducto = detalleProducto; 

    }

    // Getters y setters
    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public LocalDateTime getFechaHoraExpedicion() {
        return fechaHoraExpedicion;
    }

    public void setFechaHoraExpedicion(LocalDateTime fechaHoraExpedicion) {
        this.fechaHoraExpedicion = fechaHoraExpedicion;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDateTime getFechaHoraPago() {
        return fechaHoraPago;
    }

    public void setFechaHoraPago(LocalDateTime fechaHoraPago) {
        this.fechaHoraPago = fechaHoraPago;
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

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Usuario getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public DetalleProducto getDetalleProducto() {
        return detalleProducto;
    }

    public void setDetalleProducto(DetalleProducto detalleProducto) {
        this.detalleProducto = detalleProducto;
    }

    @Override
    public String toString() {
        return "Ticket [idTicket=" + idTicket + ", fechaHoraExpedicion=" + fechaHoraExpedicion + ", montoTotal="
                + montoTotal + ", fechaHoraPago=" + fechaHoraPago + ", montoPagado=" + montoPagado + ", cambio="
                + cambio + ", metodoPago=" + metodoPago + ", empleado=" + empleado + ", reserva=" + reserva
                + ", detalleProducto=" + detalleProducto + "]";
    }
}
