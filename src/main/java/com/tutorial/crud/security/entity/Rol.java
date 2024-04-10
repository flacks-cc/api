package com.tutorial.crud.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.tutorial.crud.security.enums.RolNombre;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rol")
	private Long idRol;

	@NotBlank
	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private RolNombre nombre;

	@ManyToMany(mappedBy = "roles")
	private Set<Usuario> usuarios = new HashSet<>();

	public Rol() {
	}

	public Rol(RolNombre nombre) {
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

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	// Método toString
	@Override
	public String toString() {
		return "Rol{" + "idRol=" + idRol + ", nombre='" + nombre + '\'' + '}';
	}
}
