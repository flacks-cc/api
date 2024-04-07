package com.tutorial.crud.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.MetodoPago;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {

    Optional<MetodoPago> findByMetodoNombre(String nombre);

    boolean existsByMetodoNombre(String nombre);
    
    List<MetodoPago> findAll();

}
