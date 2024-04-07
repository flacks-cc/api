package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Resenas;

@Repository
public interface ResenaRepository extends JpaRepository<Resenas, Integer> {
    boolean existsByFechaResena(String fechaResena);
}