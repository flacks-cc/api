package com.tutorial.crud.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	Optional<Producto> findByNombre(String nombre);

	boolean existsByNombre(String nombre);
}
