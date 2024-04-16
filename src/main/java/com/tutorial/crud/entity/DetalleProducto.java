package com.tutorial.crud.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Detalles_Productos")
public class DetalleProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_producto")
    private Long idDetalleProducto;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser mayor que cero")
    @Column(name = "cantidad", nullable = true)
    private int cantidad;

    @NotNull(message = "El total no puede ser nulo")
    @Column(name = "total", nullable = true)
    private double total;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = true, referencedColumnName = "id_reserva")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "id_producto",  nullable = true , referencedColumnName = "id_producto")
    private Producto producto;

    // Constructor vacío
    public DetalleProducto() {
    }

    // Constructor con todos los atributos
    public DetalleProducto(int cantidad, double total, Reserva reserva, Producto producto) {
        this.cantidad = cantidad;
        this.total = total;
        this.reserva = reserva;
        this.producto = producto;
    }

    // Getters y setters
    public Long getIdDetalleProducto() {
        return idDetalleProducto;
    }

    public void setIdDetalleProducto(Long idDetalleProducto) {
        this.idDetalleProducto = idDetalleProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // Método toString
    @Override
    public String toString() {
        return "DetalleProducto [idDetalleProducto=" + idDetalleProducto + ", cantidad=" + cantidad + ", total=" + total
                + ", reserva=" + reserva + ", producto=" + producto + "]";
    }
}
