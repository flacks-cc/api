package com.tutorial.crud.security.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@NotNull
	@Size(max = 50)
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@NotNull
	@Size(max = 50)
	@Column(name = "apellido_paterno", nullable = false, length = 50)
	private String apellidoPaterno;

	@Size(max = 50)
	@Column(name = "apellido_materno", length = 50)
	private String apellidoMaterno;

	@NotNull
	@Size(min = 10, max = 10, message = "El número de teléfono debe tener exactamente 10 dígitos.")
	@Column(name = "telefono", unique = true, nullable = false, length = 10)
	private String telefono;

	@NotNull
	@Column(name = "nombre_usuario", unique = true, nullable = false, length = 50)
	private String nombreUsuario;

	@NotNull
	@Size(max = 100)
	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@NotNull
	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
	private Set<Rol> roles = new HashSet<>();

	public Usuario() {
	}

	public Usuario(@NotNull String nombre, String apellidoPaterno, String apellidoMaterno, String telefono,
			@NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.telefono = telefono;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setId(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
}