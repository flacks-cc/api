package com.tutorial.crud.dto;

import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.Ticket;

public class DetalleTicketProductoDto {

	private Long idDetalleTicketProducto;

	private Ticket ticket;

	private DetalleProducto detalleProducto;

	// Constructor vacío
	public DetalleTicketProductoDto() {
	}

	// Constructor con todos los atributos
	public DetalleTicketProductoDto(Ticket ticket, DetalleProducto detalleProducto) {
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
}
