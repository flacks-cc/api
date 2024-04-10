package com.tutorial.crud.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
	Optional<Servicio> findByNombre(String nombre);

	boolean existsByNombre(String nombre);

	Optional<Long> findIdByIdServicio(Long idServicio);
}