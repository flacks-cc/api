package com.tutorial.crud.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.enums.RolNombre;

public interface RolesRepository extends JpaRepository<Rol, Long> {
	
    Optional<Rol> findByNombre(RolNombre nombre);

}
