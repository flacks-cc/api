package com.tutorial.crud.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.tutorial.crud.security.enums.RolNombre;

@Entity
@Table(name = "roles")
public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rol")
	private Long idRol;

	@NotNull
	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private RolNombre nombre;

	public Rol() {
	}

	public Rol(@NotNull RolNombre nombre) {
		this.nombre = nombre;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public RolNombre getNombre() {
		return nombre;
	}

	public void setNombre(RolNombre nombre) {
		this.nombre = nombre;
	}

	// Método toString
	@Override
	public String toString() {
		return "Rol{" + "idRol=" + idRol + ", nombre='" + nombre + '\'' + '}';
	}
}
