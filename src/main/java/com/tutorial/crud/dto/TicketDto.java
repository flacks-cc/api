package com.tutorial.crud.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.Metodo_Pago;
import com.tutorial.crud.security.entity.Usuario;

public class TicketDto {

    private int id;

    private LocalDateTime fechaImpresion;
    
    private double montoTotal; 

    private LocalDateTime fechaPago;

    private double montoPagado; 

    private double cambio; 
    
    @Size(max = 50)
    private String nombreEmpleado;

    private int idMetodoPago;

    @NotNull(message = "El id del detalle general debe ser ingresado y no puede ser nulo")
    private int idDetalleProducto;
    
    @NotNull(message = "El id del usuario debe ser ingresado y no puede ser nulo")
    private int idUsuario;
   
    private Metodo_Pago metodoPago;
    
    private DetalleProducto detalleProducto;
    
    private Usuario usuario;

    public TicketDto() {
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

    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public int getIdDetalleProducto() {
        return idDetalleProducto;
    }

    public void setIdDetalleProducto(int idDetalleProducto) {
        this.idDetalleProducto = idDetalleProducto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Metodo_Pago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(Metodo_Pago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public DetalleProducto getDetalleProducto() {
        return detalleProducto;
    }

    public void setDetalleProducto(DetalleProducto detalleProducto) {
        this.detalleProducto = detalleProducto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    public TicketDto(int id, LocalDateTime fechaImpresion, double montoTotal, LocalDateTime fechaPago, double montoPagado, double cambio, String nombreEmpleado, int idMetodoPago, int idDetalleProducto, int idUsuario, Metodo_Pago metodoPago, DetalleProducto detalleProducto, Usuario usuario) {
        this.id = id;
        this.fechaImpresion = fechaImpresion;
        this.montoTotal = montoTotal;
        this.fechaPago = fechaPago;
        this.montoPagado = montoPagado;
        this.cambio = cambio;
        this.nombreEmpleado = nombreEmpleado;
        
        if (metodoPago != null) {
            this.metodoPago = metodoPago;
            this.idMetodoPago = metodoPago.getId();
        }
        
        if (detalleProducto != null) {
            this.detalleProducto = detalleProducto;
            this.idDetalleProducto = detalleProducto.getId();
        }
        
        if (usuario != null) {
            this.usuario = usuario;
            this.idUsuario = usuario.getId();
        }
    }
    




}
