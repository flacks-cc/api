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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@NotBlank
	@Column(name = "nombre_usuario", unique = true, nullable = false, length = 50)
	private String nombreUsuario;

	@NotBlank
	@Size(max = 50)
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@NotBlank
	@Size(max = 50)
	@Column(name = "apellido_paterno", nullable = false, length = 50)
	private String apellidoPaterno;

	@Size(max = 50)
	@Column(name = "apellido_materno", length = 50)
	private String apellidoMaterno;

	@NotBlank
	@Email
	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@NotBlank
	@Size(max = 255)
	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@NotBlank
	@Size(min = 10, max = 10)
	@Column(name = "telefono", unique = true, nullable = false, length = 10)
	private String telefono;

	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
	private Set<Rol> roles = new HashSet<>();

	// Constructor vacío
	public Usuario() {
	}

	// Constructor con todos los campos
	public Usuario(String nombreUsuario, String nombre, String apellidoPaterno, String apellidoMaterno,
			String email, String password, String numeroTelefono) {
		this.nombreUsuario = nombreUsuario;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.email = email;
		this.password = password;
		this.telefono = numeroTelefono;
	}

	// Getters y setters
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
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

	public void setTelefono(String numeroTelefono) {
		this.telefono = numeroTelefono;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	// Método toString
	@Override
	public String toString() {
		return "Usuario{" + "idUsuario=" + idUsuario + ", nombreUsuario='" + nombreUsuario + '\'' + ", nombre='"
				+ nombre + '\'' + ", apellidoPaterno='" + apellidoPaterno + '\'' + ", apellidoMaterno='"
				+ apellidoMaterno + '\'' + ", email='" + email + '\'' + ", password='"
				+ password + '\'' + ", numeroTelefono='" + telefono + '}';
	}

}