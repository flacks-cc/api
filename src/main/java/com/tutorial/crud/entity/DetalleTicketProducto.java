package com.tutorial.crud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Detalles_Ticket_Producto")
public class DetalleTicketProducto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalle_ticket_producto")
	private Long idDetalleTicketProducto;

	@ManyToOne
	@NotNull(message = "El id del ticket no puede ser nulo")
	@JoinColumn(name = "id_ticket", referencedColumnName = "id_ticket", nullable = false)
	private Ticket ticket;

	@ManyToOne
	@NotNull(message = "El id del detalles de productos no puede ser nulo")
	@JoinColumn(name = "id_detalle_producto", referencedColumnName = "id_detalle_producto", nullable = false)
	private DetalleProducto detalleProducto;

	// Constructor vacío
	public DetalleTicketProducto() {
	}

	// Constructor con todos los atributos
	public DetalleTicketProducto(Ticket ticket, DetalleProducto detalleProducto) {
		this.ticket = ticket;
		this.detalleProducto = detalleProducto;
	}

	// Getters y setters
	public Long getIdDetalleTicketProducto() {
		return idDetalleTicketProducto;
	}

	public void setIdDetalleTicketProducto(Long idDetalleTicketProducto) {
		this.idDetalleTicketProducto = idDetalleTicketProducto;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public DetalleProducto getDetalleProducto() {
		return detalleProducto;
	}

	public void setDetalleProducto(DetalleProducto detalleProducto) {
		this.detalleProducto = detalleProducto;
	}

	// Método toString
	@Override
	public String toString() {
		return "DetallesTicket [idDetalleTicket=" + idDetalleTicketProducto + ", ticket=" + ticket
				+ ", detalleProducto=" + detalleProducto + "]";
	}
}
