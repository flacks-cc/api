package com.tutorial.crud.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Integer idUsuario;

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
    @Column(name = "correo_electronico", unique = true, nullable = false, length = 100)
    private String correo;

    @NotBlank
    @Size(max = 255)
    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @NotBlank
    @Size(min = 10, max = 10)
    @Column(name = "numero_telefono", unique = true, nullable = false, length = 10)
    private String telefono;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con todos los campos
    public Usuario(String nombreUsuario, String nombre, String apellidoPaterno, String apellidoMaterno,
            String correoElectronico, String contrasena, String numeroTelefono) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correoElectronico;
        this.contrasena = contrasena;
        this.telefono = numeroTelefono;
    }

    // Getters y setters
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

    public String getCorreoElectronico() {
        return correo;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correo = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNumeroTelefono() {
        return telefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.telefono = numeroTelefono;
    }
    
    // Método toString
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", correoElectronico='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", numeroTelefono='" + telefono +
                '}';
    }

}
