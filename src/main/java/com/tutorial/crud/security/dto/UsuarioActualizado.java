package com.tutorial.crud.security.dto;

import java.util.HashSet;
import java.util.Set;

import com.tutorial.crud.security.entity.Rol;

public class UsuarioActualizado {

	private Integer idUsuario;

	private String nombreUsuario;

	private String nombre;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String email;

	private String password;

	private String telefono;

	private Set<Rol> roles = new HashSet<>();

	// Constructor vacío
	public UsuarioActualizado() {
	}

	// Constructor
	public UsuarioActualizado(Integer idUsuario, String nombreUsuario, String nombre, String apellidoPaterno,
			String apellidoMaterno, String email, String password, String telefono) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.password = password;
		this.telefono = telefono;
	}

	// Getters y Setters
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
}
