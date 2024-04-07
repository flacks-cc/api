package com.tutorial.crud.dto;

import javax.validation.constraints.NotBlank;

public class MetodoPagoDto {


    @NotBlank(message = "El nombre del metood de pago es obligatorio")
    private String metodoNombre;

    public String getMetodoNombre() {
		return metodoNombre;
	}

	public void setMetodoNombre(String metodoNombre) {
		this.metodoNombre = metodoNombre;
	}

	public MetodoPagoDto() {
    }

    public MetodoPagoDto( @NotBlank String metodoNombre) {
        this.metodoNombre = metodoNombre;
    }
}
