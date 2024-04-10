package com.tutorial.crud.security.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {

	Optional<MetodoPago> findByNombre(String nombre);

	boolean existsByNombre(String nombre);

	List<MetodoPago> findAll();

}