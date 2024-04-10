package com.tutorial.crud.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.crud.security.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    Usuario findByEmail(String email);
    boolean existsByTelefono(String telefono);
    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);
    boolean existsByTelefonoAndIdNot(String telefono, int id);
    boolean existsByNombreUsuarioAndIdNot(String nombreUsuario, int id);
    boolean existsByEmailAndIdNot(String email, int id);
}
