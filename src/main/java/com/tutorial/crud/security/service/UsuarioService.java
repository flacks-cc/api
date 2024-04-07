package com.tutorial.crud.security.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(int id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }


    public boolean existsByTelefonoAndIdNot(String telefono, int id) {
        return usuarioRepository.existsByTelefonoAndIdNot(telefono, id);
    }

    public boolean existsByNombreUsuarioAndIdNot(String nombreUsuario, int id) {
        return usuarioRepository.existsByNombreUsuarioAndIdNot(nombreUsuario, id);
    }

    public boolean existsByEmailAndIdNot(String email, int id) {
        return usuarioRepository.existsByEmailAndIdNot(email, id);
    }

    public boolean existsByTelefono(String telefono) {
        return usuarioRepository.existsByTelefono(telefono);
    }
    
    public void deleteById(int id) {
        usuarioRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return usuarioRepository.existsById(id);
    }

    public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Usuario getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreUsuario, String email) {
        return usuarioRepository.findByNombreUsuarioOrEmail(nombreUsuario, email);
    }

}
