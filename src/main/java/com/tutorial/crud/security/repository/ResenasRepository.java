package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Resena;

@Repository
public interface ResenasRepository extends JpaRepository<Resena, Integer> {
    boolean existsByFechaHora(String fechaHora);
}