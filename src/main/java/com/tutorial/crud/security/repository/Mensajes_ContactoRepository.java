package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    boolean existsByFechaMensaje(String fechaMensaje);
}
