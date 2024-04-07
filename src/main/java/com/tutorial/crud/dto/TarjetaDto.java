package com.tutorial.crud.dto;

import javax.validation.constraints.NotNull;

public class TarjetaDto {

    private int id;

    @NotNull(message = "El uid de la tarjeta debe ser ingresado y no puede ser nulo")
    private String uid;

    @NotNull(message = "El id de usuario debe ser ingresado y no puede ser nulo")
    private Integer idUsuario;

    // Constructor
    public TarjetaDto() {
    }
    

    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public TarjetaDto(int id,  String uid, int idUsuario) {
        this.id = id;
        this.uid = uid;
        this.idUsuario = idUsuario;
    }


    // Getters y setters

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}
