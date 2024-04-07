package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.DetalleGeneral;

@Repository
public interface DetalleGeneralRepository extends JpaRepository<DetalleGeneral, Integer> {
}
