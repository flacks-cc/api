package com.tutorial.crud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Metodos_Pago")
public class Metodo_Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    private Long idMetodoPago;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 50, message = "Se acepta un máximo de 50 caracteres")
    @Column(name = "nombre", unique = true, nullable = false, length = 50)
    private String nombre;

    
    // Constructor vacío
    public Metodo_Pago() {
    }

    // Constructor lleno
    public Metodo_Pago(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(Long idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método toString
    @Override
    public String toString() {
        return "MetodoPago [idMetodoPago=" + idMetodoPago + 
        		", nombre=" + nombre + "]";
    }
}
