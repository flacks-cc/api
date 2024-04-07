package com.tutorial.crud.security.dto;

import java.util.List;

public class UsuarioActualizado {

private String nombre;
private String apellidoPaterno;
private String apellidoMaterno;
private String telefono;
private String nombreUsuario;
private String email;
private String password;
private List<String> roles; // Puedes usar una lista para los roles

// Constructor, getters y setters aquí (según tus necesidades)

// Ejemplo de constructor
public UsuarioActualizado(String nombre, String apellidoPaterno, String apellidoMaterno,
                         String telefono, String nombreUsuario, String email, String password,
                         List<String> roles) {
    this.nombre = nombre;
    this.apellidoPaterno = apellidoPaterno;
    this.apellidoMaterno = apellidoMaterno;
    this.telefono = telefono;
    this.nombreUsuario = nombreUsuario;
    this.email = email;
    this.password = password;
    this.roles = roles;
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

public String getTelefono() {
	return telefono;
}

public void setTelefono(String telefono) {
	this.telefono = telefono;
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

public List<String> getRoles() {
	return roles;
}

public void setRoles(List<String> roles) {
	this.roles = roles;
}
}