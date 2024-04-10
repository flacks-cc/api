package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.Resena;

public interface ResenasRepository extends JpaRepository<Resena, Long> {

	boolean existsByFechaHora(String fechaHora);

}