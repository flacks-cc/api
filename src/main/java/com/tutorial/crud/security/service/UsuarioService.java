package com.tutorial.crud.security.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.repository.UsuariosRepository;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuariosRepository usuariosRepository;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
        return usuariosRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario) {
        return usuariosRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String correo) {
        return usuariosRepository.existsByEmail(correo);
    }

    public void save(Usuario usuario) {
        usuariosRepository.save(usuario);
    }

    public Optional<Usuario> findById(int idUsuario) {
        return usuariosRepository.findById(idUsuario);
    }

    public List<Usuario> findAll() {
        return usuariosRepository.findAll();
    }


    public boolean existsByTelefonoAndIdNot(String telefono, int idUsuario) {
        return usuariosRepository.existsByTelefonoAndIdNot(telefono, idUsuario);
    }

    public boolean existsByNombreUsuarioAndIdNot(String nombreUsuario, int idUsuario) {
        return usuariosRepository.existsByNombreUsuarioAndIdNot(nombreUsuario, idUsuario);
    }

    public boolean existsByEmailAndIdNot(String correo, int idUsuario) {
        return usuariosRepository.existsByEmailAndIdNot(correo, idUsuario);
    }

    public boolean existsByTelefono(String telefono) {
        return usuariosRepository.existsByTelefono(telefono);
    }
    
    public void deleteById(int idUsuario) {
        usuariosRepository.deleteById(idUsuario);
    }

    public boolean existsById(int idUsuario) {
        return usuariosRepository.existsById(idUsuario);
    }

    public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
        return usuariosRepository.findByNombreUsuario(nombreUsuario);
    }

    public Usuario getByEmail(String correo) {
        return usuariosRepository.findByEmail(correo);
    }

    public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreUsuario, String correo) {
        return usuariosRepository.findByNombreUsuarioOrEmail(nombreUsuario, correo);
    }

}
