package com.tutorial.crud.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.enums.RolNombre;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByNombreUsuario(String nombreUsuario);

	boolean existsByNombreUsuario(String nombreUsuario);

	boolean existsByEmail(String email);

	Usuario findByEmail(String email);

	boolean existsByTelefono(String telefono);

	Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);

	boolean existsByTelefonoAndIdUsuarioNot(String telefono, Long idUsuario);

	boolean existsByNombreUsuarioAndIdUsuarioNot(String nombreUsuario, Long idUsuario);

	boolean existsByEmailAndIdUsuarioNot(String email, Long idUsuario);
	
    long countByRolesNombre(RolNombre rolNombre);


}
