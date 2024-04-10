package com.tutorial.crud.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.crud.security.entity.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String correo);
    Usuario findByEmail(String correo);
    boolean existsByTelefono(String telefono);
    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String correo);
    boolean existsByTelefonoAndIdNot(String telefono, int idUsuario);
    boolean existsByNombreUsuarioAndIdNot(String nombreUsuario, int idUsuario);
    boolean existsByEmailAndIdNot(String correo, int idUsuario);
}
