package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.MensajeContacto;

public interface MensajeContactoRepository extends JpaRepository<MensajeContacto, Long> {

	boolean existsByFechaHora(String fechaHora);
}