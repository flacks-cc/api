package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Mensaje_Contacto;

@Repository
public interface Mensajes_ContactoRepository extends JpaRepository<Mensaje_Contacto, Integer> {
    boolean existsByFechaHora(String fechaHora);
}
